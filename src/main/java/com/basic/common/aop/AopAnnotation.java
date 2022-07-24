package com.basic.common.aop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.basic.common.cnst.Const;
import com.basic.common.model.ParamMap;
import com.basic.common.utils.ClientUtils;
import com.basic.common.utils.DateUtils;
import com.basic.common.utils.MessageUtils;
import com.basic.common.utils.NtnoCrtnUtils;
import com.basic.common.utils.StringUtils;




/**
 * <pre>
 * 액션 로그 파라미터값 설정
 * </pre>
 *
 * @ClassName   : AopAnnotation.java
 * @Description : 액션 로그 파라미터값 설정
 * @author cyk
 * @since 2020. 10. 20.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 10. 20.     cyk       최초 생성
 * </pre>
 */

@Aspect
public class AopAnnotation {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NtnoCrtnUtils ntnoCrtnUtils;

    @Autowired
    private MessageUtils messageUtils;

    @SuppressWarnings("unchecked")
    @Around("execution(* com.basic.api.*.web.*Controller.*(..))")
    public Object setAround(ProceedingJoinPoint joinPoint) throws Throwable {
    	try {
	    	JSONObject result= new JSONObject();
	        String type = joinPoint.getSignature().toShortString();
	        Object[] args = joinPoint.getArgs();

	        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	        HttpSession session = request.getSession();

	        String sessGuid = StringUtils.nvl(session.getAttribute("SESS_GUID"), "");

	        if("".equals(sessGuid)){
	            String key = ntnoCrtnUtils.occrSessionGuid();
	            session.setAttribute("SESS_GUID", key);
	            sessGuid = key;
	        }

	        logger.info("SESS_GUID = {}, ===================START=================== IP : {}", sessGuid, ClientUtils.getRemoteIP(request));

	        String ip = ClientUtils.getRemoteIP(request);
	        String reqDate = "";

	        boolean logYn = false;

	        for(Object obj : args){
	            if(obj instanceof HttpServletRequest && !logYn){
	            	logYn = true;

	                logger.info("SESS_GUID = {}, @Around : {}, reqParam : {}", sessGuid, type, map2str(request.getParameterMap()));

	                reqDate = StringUtils.nvl(request.getParameter("reqDate"));

	                //클라이언트에서 호출되는 화면은 IP 체크 제외
	                if(!(type.indexOf("chargePCash(") > -1 || type.indexOf("requestPayJoin(") > -1 || type.indexOf("setPay(") > -1 ||
	                		type.indexOf("requestPay(") > -1 || type.indexOf("requestPgPc(") > -1 || type.indexOf("requestPgMo(") > -1 ||
	                		type.indexOf("getPgPcAuthResult(") > -1 || type.indexOf("getPgMoAuthResult(") > -1 || type.indexOf("refundPCash(") > -1 ||
	                		type.indexOf("refundPCashResult(") > -1)) {
		            	String systemCd = "";

		                systemCd = StringUtils.nvl(request.getParameter("reqSysCode"), "");

		                if("".equals(systemCd)) {
		                	systemCd = "BPF";
		                }

		                if(!(type.indexOf("apiTest") > -1 || type.indexOf("processDepositPc(") > -1 || type.indexOf("processDepositMo(") > -1)) {
		                	ParamMap paramMap = new ParamMap();

		                	paramMap.put("systemCd", systemCd);
		                	paramMap.put("idVr", "MER".equals(systemCd) ? request.getParameter("vrId") : "*");//가맹점 일 경우만 아이디 검색
		                	paramMap.put("ipAddress", ip);
		                	paramMap.put("cdStatus", "A");

	//	                	List<EgovMap> list = commonService.selectRpTbAccessSvrIpList(paramMap);
	//
	//	                	if(list.size() == 0){
	//	                		result.put("resultCode", messageUtils.getCode("validate.access.ip"));
	//	            			result.put("resultMsg", messageUtils.getMsg("validate.access.ip"));
	//
	//	            			return result;
	//	                	}
		                }
	                }
	            }else if(obj instanceof BindingResult){
	                BindingResult br = (BindingResult)obj;
	                StringBuilder sb = new StringBuilder();

	                if(br.hasErrors()) {
	                    List<FieldError> errors = br.getFieldErrors();
	                    for(FieldError error : errors) {
	                        String[] msg = messageUtils.getMessage(error.getDefaultMessage()).split("\\|");
	                        sb.append("[" + error.getField() + ":" + msg[1] + "|" + msg[0] + "] ");
	                    }

	                    logger.info("SESS_GUID = {}, @Valid : BindingResult, Msg : {} ", sessGuid, sb.toString());

	                    String[] msg = messageUtils.getMessage(errors.get(0).getDefaultMessage()).split("\\|");
	                    result.put("resultCode", msg[1]);
	                    result.put("resultMsg", StringUtils.URLEncode(errors.get(0).getField() + " " + msg[0]));

	                    return result;
	                }else {
	                    //reqDate 체크. 현재시간 기준으로 300초 넘게 차이나면 오류 리턴
	                	if(!"".equals(reqDate)) {
	                		String envProfile = System.getProperty(Const.ENV_PROFILE);

	                    	if(!("default".equals(envProfile) || "dev".equals(envProfile))) {//테스트를 위해 로컬과 dev에선 체크안함
	                    		Long chkTime = (long) 300;
	                    		reqDate = StringUtils.nvl(br.getRawFieldValue("reqDate"));
	                        	String now = DateUtils.getSecTime();

	        					Long diffSec = DateUtils.getSecBetween(now, reqDate);
	                        	logger.info("SESS_GUID = {}, @Valid now = {}, diffSec = {}",sessGuid, now, diffSec);
	                        	diffSec = Math.abs(diffSec);

	                        	if(diffSec > chkTime) {
	                        		logger.info("SESS_GUID = {}, @Valid : BindingResult, reqDate = {}, now = {}",sessGuid, reqDate, now);
	                        		result.put("resultCode", messageUtils.getCode("validate.reqDate.error"));
	                                result.put("resultMsg", messageUtils.getMsg("validate.reqDate.error"));

	                                return result;
	                        	}
	                    	}

	                    	logger.info("SESS_GUID = {}, @Valid : BindingResult, Msg : {} ", sessGuid, "success");
	                	}
	                }
	            }else {
	            	if(!logYn) {
	            		logYn = true;
	            		logger.info("SESS_GUID = {}, @Around : {}, param : {}", sessGuid, type, obj.toString());

	                	Map<String, String> param = new HashMap<String, String>();

	                	if(args[0].toString().indexOf("VO") > -1){
	                        param = convertParamVOToMap(args[0].toString(), "=");
	                	}else {
	                        param = convertParamToMap(args[0].toString(), ":");
	                	}

	                	reqDate = StringUtils.nvl(param.get("reqDate"));

	                	//클라이언트에서 호출되는 화면은 IP 체크 제외
	                    if(!(type.indexOf("chargePCash(") > -1 || type.indexOf("requestPayJoin(") > -1 || type.indexOf("setPay(") > -1 ||
	                    		type.indexOf("requestPay(") > -1 || type.indexOf("requestPgPc(") > -1 || type.indexOf("requestPgMo(") > -1 ||
	                    		type.indexOf("getPgPcAuthResult(") > -1 || type.indexOf("getPgMoAuthResult(") > -1 || type.indexOf("refundPCash(") > -1 ||
	                    		type.indexOf("refundPCashResult(") > -1 )) {
		            		String systemCd = "";

		            		systemCd = StringUtils.nvl(param.get("reqSysCode"), "");

		                    if("".equals(systemCd)) {
		                    	systemCd = "BPF";
		                    }

		                    if(!(type.indexOf("apiTest") > -1 || type.indexOf("processDepositPc(") > -1 || type.indexOf("processDepositMo(") > -1)) {
			                    ParamMap paramMap = new ParamMap();

			                    paramMap.put("systemCd", systemCd);
			                	paramMap.put("idVr", "MER".equals(systemCd) ? param.get("vrId") : "*");//가맹점 일 경우만 아이디 검색
			                	paramMap.put("ipAddress", ip);
			                	paramMap.put("cdStatus", "A");

	//		                	List<EgovMap> list = commonService.selectRpTbAccessSvrIpList(paramMap);
	//
	//		                	if(list.size() == 0){
	//		                		result.put("resultCode", messageUtils.getCode("validate.access.ip"));
	//		            			result.put("resultMsg", messageUtils.getMsg("validate.access.ip"));
	//
	//		            			return result;
	//		                	}
		                    }
	                    }
	            	}
	            }
	        }

    	}catch (Exception e){
        	StringUtils.getError(e);
        }

        return joinPoint.proceed();
    }

    @Before("execution(* com.basic.api.*.service.impl.*.*(..))")
    public void setBefore(JoinPoint joinPoint) throws Throwable {
        String type = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        String sessGuid = String.valueOf(session.getAttribute("SESS_GUID"));

        if(args.length == 0 || args[0] instanceof HttpServletRequest){
            logger.info("SESS_GUID = {}, @Before : {}, param : {}", sessGuid, type, map2str(request.getParameterMap()));
        }else{
    		logger.info("SESS_GUID = {}, @Before : {}, param : {}", sessGuid, type, args[0].toString());
        }
    }

    @AfterReturning(pointcut="execution(* com.basic.api.*.web.*Controller.*(..)) || execution(* com.basic.api.*.service.impl.*.*(..))", returning="retValue")
    public void setAfterReturning(JoinPoint joinPoint, Object retValue) throws Throwable {

        String type = joinPoint.getSignature().toShortString();
        String typeSimple = joinPoint.getSignature().getDeclaringType().getSimpleName();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        String sessGuid = session.getAttribute("SESS_GUID").toString();

        if(typeSimple.indexOf("Controller") != -1){
            logger.info("SESS_GUID = {}, @After(C) : {}, result : {}", sessGuid, type, retValue);

            logger.info("SESS_GUID = {}, ===================E N D===================", sessGuid);
        }else {
            logger.info("SESS_GUID = {}, @After : {}, result : {}", sessGuid, type, retValue);
        }
    }

	@AfterThrowing(value="execution(* com.basic.api.*.web.*Controller.*(..))", throwing="e")
    public void setAfterThrowing(JoinPoint joinPoint, Exception e) throws Throwable {

        String type = joinPoint.getSignature().toShortString();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        String sessGuid = session.getAttribute("SESS_GUID").toString();

        if(e.getMessage() == null){
            logger.error("SESS_GUID = {}, @Throwing : {}, {}", sessGuid, type, e.getStackTrace()[0]);
            logger.error("SESS_GUID = {}, StackTrace : {}", sessGuid, StringUtils.getError(e));
        }else{
            logger.error("SESS_GUID = {}, @Throwing : {}, {}", sessGuid, type, e.getMessage());
            logger.error("SESS_GUID = {}, StackTrace : {}", sessGuid, StringUtils.getError(e));
        }
    }

    public String map2str(Map<String, String[]> map){
        if(map == null || map.keySet().size() == 0){
            return "{null}";
        }

        StringBuffer sb = new StringBuffer();
        sb.append("{");
        for(String key : map.keySet()){
            Object val = map.get(key);

            if(val == null){
                sb.append(key + "null");
            }else if(val instanceof String){
                sb.append(key + ":" + val);
            }else if(val instanceof String[]){
                String[] arr = (String[])val;
                if(arr.length == 1){
                    sb.append(key + ":" + arr[0]);
                }else{
                    for(int i=0; i < arr.length; i++){
                        sb.append(key + ":" + arr[i]);
                    }
                }
            }else{
                sb.append(key + ":" + val.toString());
            }

            sb.append(", ");
        }
        sb.append("}");

        return sb.toString().replace(", }", "}");
    }

    public String setParamParse(String str, String gubun){
        Map<String, String> map = new HashMap<String, String>();

        if(str == null || "".equals(str)){
            return null;
        }

        if(str.indexOf("{") < 0){
            return str;
        }

        str = str.replace("{", "");
        str = str.replace("}", "");

        String[] array = str.split(",");

        for(int i = 0; i < array.length; i++){
            String[] param = array[i].split(gubun);
            if(param.length > 1){
                String name = param[0];
                String value = param[1];

                map.put(name, value);
            }else{
                if(param.length == 0) {
                    map.put("", "");
                }else {
                    String name = param[0];
                    map.put(name, "");
                }
            }
        }

        StringBuffer sb = new StringBuffer();
        sb.append("{");
        for(String key : map.keySet()){
            Object val = map.get(key);
            key = key.trim();

            if(val == null){
                sb.append(key + "null");
            }else if(val instanceof String){
                sb.append(key + gubun + val);
            }else if(val instanceof String[]){
                String[] arr = (String[])val;
                if(arr.length == 1){
                    sb.append(key + gubun + arr[0]);
                }else{
                    for(int i=0; i < arr.length; i++){
                        sb.append(key + gubun + arr[i]);
                    }
                }
            }else{
                sb.append(key + gubun + val.toString());
            }

            sb.append(", ");
        }
        sb.append("}");

        return sb.toString().replace(", }", "}");
    }

    public String setParamParseVO(String str, String gubun){
        if(str == null || "".equals(str)){
            return null;
        }

        if(str.indexOf("[") < 0){
            return str;
        }

        StringBuffer sb = new StringBuffer();
        Boolean chkList = false;

        if(str.substring(0, 1).equals("[")){
            chkList = true;
            str = str.substring(1,str.length()-2);
            sb.append("[");
        }else{
            str = str.substring(0,str.length()-1);
        }

        String[] array = str.split("],");

        for(int i = 0; i < array.length; i++){
            String[] voList = array[i].split(",");
            Map<String, String> map = new HashMap<String, String>();

            for(int j = 0; j < voList.length; j++){
                if(j == 0){
                    String[] param = voList[j].split("\\[");
                    if(param.length > 1){
                        String[] param2 = param[1].split(gubun);
                        String name = param2[0];
                        String value = param2[1];
                        sb.append(param[0].trim() + " [");
                        map.put(name, value);
                    }else{
                        String name = param[0];
                        String value = "";
                        sb.append(param[0].trim() + " [");
                        map.put(name, value);
                    }

                }else{
                    String[] param = voList[j].split(gubun);
                    if(param.length > 1){
                        String name = param[0];
                        String value = param[1];

                        map.put(name, value);
                    }else{
                        String name = param[0];

                        map.put(name, "");
                    }

                }
            }

            for(String key : map.keySet()){
                Object val = map.get(key);
                key = key.trim();

                if(val == null){
                    sb.append(key + "null");
                }else if(val instanceof String){
                    sb.append(key + gubun + val);
                }else if(val instanceof String[]){
                    String[] arr = (String[])val;
                    if(arr.length == 1){
                        sb.append(key + gubun + arr[0]);
                    }else{
                        for(int z=0; i < arr.length; z++){
                            sb.append(key + gubun + arr[z]);
                        }
                    }
                }else{
                    sb.append(key + gubun + val.toString());
                }

                sb.append(", ");
            }
            sb.append("], ");
        }

        String rtnStr = sb.toString().replace(", ]", "]");
        rtnStr = rtnStr.substring(0, rtnStr.length()-2);

        if(chkList){
            rtnStr = rtnStr + "]";
        }

        return rtnStr;
    }

    public Map<String, String> convertParamToMap(String str, String gubun){
        Map<String, String> map = new HashMap<String, String>();

        if(str == null || "".equals(str)){
            return null;
        }

        str = str.replace("{", "");
        str = str.replace("}", "");

        String[] array = str.split(",");

        for(int i = 0; i < array.length; i++){
            String[] param = array[i].split(gubun);
            if(param.length > 1){
                String name = param[0].trim();
                String value = param[1].trim();

                map.put(name, value);
            }else{
                if(param.length == 0) {
                    map.put("", "");
                }else {
                    String name = param[0].trim();
                    map.put(name, "");
                }
            }
        }

        return map;
    }

    public Map<String, String> convertParamVOToMap(String str, String gubun){
    	Map<String, String> map = new HashMap<String, String>();

        if(str == null || "".equals(str)){
            return null;
        }

        if(str.substring(0, 1).equals("[")){
            str = str.substring(1,str.length()-2);
        }else{
            str = str.substring(0,str.length()-1);
        }

        String[] array = str.split("],");

        for(int i = 0; i < array.length; i++){
            String[] voList = array[i].split(",");

            for(int j = 0; j < voList.length; j++){
                if(j == 0){
                    String[] param = voList[j].split("\\[");
                    if(param.length > 1){
                        String[] param2 = param[1].split(gubun);
                        String name = param2[0].trim();
                        String value = "";
                        if(param2.length > 1) {
                        	value = param2[1].trim();
                        }else {
                        	value = "";
                        }

                        map.put(name, value);
                    }else{
                        String name = param[0].trim();
                        String value = "";
                        map.put(name, value);
                    }

                }else{
                    String[] param = voList[j].split(gubun);
                    if(param.length > 1){
                        String name = param[0].trim();
                        String value = param[1].trim();

                        map.put(name, value);
                    }else{
                        String name = param[0].trim();

                        map.put(name, "");
                    }
                }
            }
        }

        return map;
    }
}