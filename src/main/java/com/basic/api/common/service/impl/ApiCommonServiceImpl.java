package com.basic.api.common.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.basic.api.common.service.ApiCommonService;
import com.basic.api.common.vo.BPA2001VO;
import com.basic.common.AppPropertiesLoader;
import com.basic.common.model.ParamMap;
import com.basic.common.utils.MessageUtils;
import com.basic.common.utils.StringUtils;
import com.basic.common.utils.crypto.SHA256;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.json.simple.JSONObject;


@Service
public class ApiCommonServiceImpl implements ApiCommonService{

	@Resource(name="ApiCommonMapper")
	ApiCommonMapper apiCommonMapper;

	@Autowired
    AppPropertiesLoader appPropertiesLoader;

    @Autowired
    MessageUtils messageUtils;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @SuppressWarnings("unchecked")
	@Override
	public JSONObject getCodeData(BPA2001VO bpa2001vo) {
		// TODO getCodeData 코드조회
		JSONObject result = new JSONObject();
		result.put("resultCode", "");
        result.put("resultMsg", "");
        result.put("codeList", "");

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String sessGuid = StringUtils.nvl(session.getAttribute("SESS_GUID"), "");

		try {
	        String hashKey = appPropertiesLoader.getValue(bpa2001vo.getReqSysCode());

	        SHA256 sha256 = new SHA256();

	        String sig = sha256.doCryption("BPA2001" + bpa2001vo.getReqSysCode() + bpa2001vo.getReqDate() + hashKey);

	        if(!sig.equals(bpa2001vo.getSignature())) {
	            result.put("resultCode", messageUtils.getCode("validate.signature"));
	            result.put("resultMsg", messageUtils.getMsg("validate.signature"));

	            return result;
	        }

	        ParamMap paramMap = new ParamMap();
	        paramMap.put("idCode", bpa2001vo.getCodeId());
	        paramMap.put("cdCodeVal", bpa2001vo.getCodeCd());

	        //코드조회
	        List<EgovMap> list = apiCommonMapper.selectRpTbCodeDataList(paramMap);

	        if(list.size() > 0) {
	            result.put("resultCode", messageUtils.getCode("success"));
	            result.put("resultMsg", messageUtils.getMsg("success"));
	            result.put("codeList", list);
	        }else {
	            result.put("resultCode", messageUtils.getCode("success.nodata"));
	            result.put("resultMsg", messageUtils.getMsg("success.nodata"));
	        }
		}catch (Exception e) {
			logger.error("SESS_GUID = {}, @Throwing getCodeData() Exception : {}", sessGuid, e.getMessage());
            logger.error("SESS_GUID = {}, StackTrace : {}", sessGuid, StringUtils.getError(e));

            result.put("resultCode", messageUtils.getCode("excption"));
			result.put("resultMsg", messageUtils.getMsg("excption"));
		}

        return result;
	}

}
