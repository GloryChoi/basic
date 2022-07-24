package com.basic.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 클라이언트 정보
 * </pre>
 *
 * @ClassName   : ClientUtils.java
 * @Description : 클라이언트 정보 관리
 * @author BWC086
 * @since 2018. 8. 20.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2018. 8. 20.     BWC086       최초 생성
 * </pre>
 */

public class ClientUtils {

    /**
     *
     * Client IP 반환
     *
     * @param request
     * @return
     */
    public static String getRemoteIP(HttpServletRequest request){
        String ip = request.getHeader("X-FORWARDED-FOR");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ("0:0:0:0:0:0:0:1".equals(ip)? "127.0.0.1" : ip);
    }

    /**
     *
     * 디바이스 코드 반환
     *
     * @param request
     * @return
     */
    public static String getDeviceCd(HttpServletRequest request){
        String dvceCd = "";
        if(request.getHeader("User-Agent").indexOf("Mobile") > -1 || request.getHeader("User-Agent").indexOf("Android") > -1 || request.getHeader("User-Agent").indexOf("iPhone") > -1){
            dvceCd = "MO";
        }else{
            dvceCd = "PC";
        }

        return dvceCd;
    }
}
