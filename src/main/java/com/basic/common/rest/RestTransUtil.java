package com.basic.common.rest;

import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.basic.common.AppPropertiesLoader;
import com.basic.common.utils.MessageUtils;
import com.basic.common.utils.StringUtils;

/**
 * <pre>
 * GateWay 전송
 * </pre>
 *
 * @ClassName   : RestTransUtil.java
 * @Description : GateWay 전송
 * @author cyk
 * @since 2020. 12. 04.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 12. 04.     cyk     	최초 생성
 * </pre>
 */

@Service
public class RestTransUtil {

    @Autowired
    RestTemplateHttp restTemplate;

    @Autowired
    AppPropertiesLoader appPropertiesLoader;

    @Autowired
    MessageUtils messageUtils;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @SuppressWarnings("unchecked")
    public JSONObject restSend(String Url, JSONObject inDataSet) throws Exception{

        JSONObject dataSet = new JSONObject();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String sessGuid = StringUtils.nvl(session.getAttribute("SESS_GUID"));

        try {
            logger.info("SESS_GUID = {}, @Before : restSend() Url : {}, param : {}", sessGuid, Url, inDataSet);

            dataSet = restTemplate.getHttpsTemplate().postForObject(Url, inDataSet, JSONObject.class);

            logger.info("SESS_GUID = {}, @After : restSend() returnData : {}", sessGuid, dataSet);
        } catch (Exception e) {
            logger.error("SESS_GUID = {}, restSend() Exception : {}", sessGuid, e.getMessage());
            dataSet.put("resultCode", messageUtils.getCode("rest.api.error"));
            dataSet.put("resultMsg", messageUtils.getCode("rest.api.error"));
            logger.error("SESS_GUID = {}, StackTrace : {}", sessGuid, StringUtils.getError(e));
        }

        logger.info("SESS_GUID = {}, @RestAPI : restSend() ####E N D####", sessGuid);

        return dataSet;
    }
}
