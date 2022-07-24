package com.basic.api.test.web;

import java.io.BufferedOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.basic.api.test.vo.ApiTestVO;
import com.basic.common.AppPropertiesLoader;
import com.basic.common.utils.MessageUtils;
import com.basic.common.utils.StringUtils;
import com.basic.common.utils.crypto.AES128;
import com.basic.common.utils.crypto.AES256;
import com.basic.common.utils.crypto.SHA256;
import com.basic.common.utils.crypto.SHA512;
import com.basic.common.utils.crypto.SeedUtil;

/**
 * @author cyk
 * @description 공통
 */
@Controller
public class ApiTestController {

    @Autowired
    MessageUtils messageUtils;

    @Autowired
    AppPropertiesLoader appPropertiesLoader;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
	 * @param request
	 * @throws Exception
	 * @description api 화면UI 테스트 화면
	 */
	@RequestMapping(value = "/api/apiTest", method = RequestMethod.GET)
    public String test(HttpServletRequest request, Model model) {
		// TODO apiTest

		model.addAttribute("apiUrl", StringUtils.URLEncode(appPropertiesLoader.getValue("api.url")));

        return "/api/apiTest";
    }

	/**
     * @param request
     * @return
     * @description aes256 암호화
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/api/encryptAes256", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject encryptAes256(HttpServletRequest request) {
    	// TODO encryptAes256
        JSONObject result= new JSONObject();

        String key = request.getParameter("key");
        String value = request.getParameter("value");

        AES256 aes256 = new AES256(key);

        String encValue = aes256.encrypt(value);

        result.put("encValue", encValue);

        return result;
    }

    /**
     * @param request
     * @return
     * @description aes256 복호화
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/api/decryptAes256", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject decryptAes256(HttpServletRequest request) {
    	// TODO decryptAes256
        JSONObject result= new JSONObject();

        String key = request.getParameter("key");
        String value = request.getParameter("value");
        value = value.replaceAll(" ", "+");
        AES256 aes256 = new AES256(key);

        String decValue = aes256.decrypt(value);

        result.put("decValue", decValue);

        return result;
    }

    /**
     * @param request
     * @return
     * @description sha256 암호화
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/api/encryptSha256", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject encryptSha256(HttpServletRequest request) {
    	// TODO encryptSha256
        JSONObject result= new JSONObject();

        String reqSysId = request.getParameter("reqSysId");
        String reqSysCode = request.getParameter("reqSysCode");
        String reqDate = request.getParameter("reqDate");
        String reqAmt = StringUtils.nvl(request.getParameter("reqAmt"),"");
        String hashKey = request.getParameter("hashKey");

        SHA256 sha256 = new SHA256();

        String value = "";

        if("".equals(reqAmt)) {
        	value = reqSysId+reqSysCode+reqDate+hashKey;
        }else {
        	value = reqSysId+reqSysCode+reqDate+reqAmt+hashKey;
        }

        String encValue = sha256.doCryption(value);

        result.put("encValue", encValue);

        return result;
    }

    /**
     * @param param
     * @param br
     * @return
     * @description 테스트
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "unused" })
    @RequestMapping(value = "/api/validTest", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject validTest(@RequestBody @Valid ApiTestVO param, BindingResult br) {
    	// TODO validTest
        JSONObject result= new JSONObject();

        try {
        	logger.info("StringUtils.checkNumber : {}", StringUtils.checkNumber("1234"));

            SeedUtil seed = new SeedUtil();
            SHA256 sha256 = new SHA256();

            String seedkey = "FzLYqYNDjk/n+FTIGAD/ng==";
  			String seediv = "WPAYINIWPAYTST00";
  			//F3149950A7B6289723F325833F588INI

  			logger.info("seed enc : {}", seed.encrypt(seedkey, seediv, "wpayTestUser01"));
  			logger.info("seed des : {}", seed.decrypt(seedkey, seediv, "0t++HNQvODa2olM8soHLhg=="));

  			sha256.doCryption("mid=INIWPAYTST&userId=+MgCYQ7hGTNSA4IpsJ/eCw==&wpayUserKey=dcVXhq2aeV6yEfRJ5E3Bx+2PzRLuk27INdjUJxzObEw=&ci=j7AmJpHQkW7JFdSsWlOsKFsTiyIqQgjlTiOjD1DsMvNnSiAXsN8dDTXumH18YN7fRjZQOm7P5ujmZqANCRsMLMCeHLqihnEfbVahspVSy4Nt19dtCXCRuQpb8eTkiH2w&hashKey=F3149950A7B6289723F325833F588INI");

  			seedkey = "g8I6LmHabx8K/yyYeXYXDQ==";
  			seediv = "WPAYSOFTFORUMT00";

            String sOutput          = null;
        	String sDecoded         = null;
        	String sOutput_H        = null;

    		String aes128key = "ItEQKi3rY7uvDS8l";
  			String aes128iv = "HYb3yQ4f65QL89==";

    		AES128 aes128 = new AES128(aes128key, aes128iv);

    		String aes128result = aes128.encrypt("5l8uENBFbTe50/9F3/7o0g==");

    		SHA512 sha512 = new SHA512();

    		String sha512result = sha512.doCryption("ItEQKi3rY7uvDS8lRefundCard20191128121211123.123.123.123INIpayTestStdpayCARDINIpayTest20191128121211123456");


//    		URL sendUrl = new URL("https://");
//
//	        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
//            sslContext.init(null, null, null);
//
//			HttpsURLConnection uc = (HttpsURLConnection) sendUrl.openConnection();
//			uc.setDoOutput(true);	// POST
//			uc.setRequestMethod("POST");
//			uc.setRequestProperty("Content-Type", "application/json;charset=utf-8");
//			uc.setSSLSocketFactory(sslContext.getSocketFactory());
//
//			OutputStream raw = uc.getOutputStream();
//			OutputStream buffered = new BufferedOutputStream(raw);
//			OutputStreamWriter osw = new OutputStreamWriter(buffered, "UTF-8");
//			osw.write("{\"reqSysId\":\"BPA1006\",\"reqSysCode\":\"MER\",\"reqDate\":\"20210503110700\",\"vrId\":\"hancomgold\",\"txtBxaid\":\"A20210430183140F8EA6\",\"vrOrderId\":\"hancomgold_20210430183129\",\"signature\":\"b62f4bbefd9cc7b94765b7f0dd3a59423cce2a5672c26c95099987d0c5181f63\"}");
//			osw.flush();
//			osw.close();
//
//			InputStreamReader isr = new InputStreamReader(uc.getInputStream(), "UTF-8");
//
//			JSONObject object = (JSONObject)JSONValue.parse(isr);
//
//			logger.info("test() res : {}", object.toString());

            result.put("resultCode", messageUtils.getCode("success"));
    		result.put("resultMsg", messageUtils.getMsg("success"));
        }catch (Exception e) {
        	logger.error(StringUtils.getError(e));
        }

        return result;
    }
}
