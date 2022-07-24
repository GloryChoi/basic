package com.basic.common.utils;

import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Component;

/**
 * <pre>
 * 고유번호생성유틸
 * </pre>
 *
 * @ClassName   : NtnoCrtnUtils.java
 * @Description : 고유번호를 생성한다.
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

@Component
public class NtnoCrtnUtils {

    /**
     *
     * 거래GUID 생성
     *
     * @return
     */
    public String occrTrxGuid() {
        String trxGuidPrifix = "A";

        return trxGuidPrifix + systemPrefix() + DateUtils.getMillisecTime() + occrRmno(2) + occrSqnc(2);
    }

    /**
     * 세션GUID 생성
     * Statements
     *
     * @return
     */
    public String occrSessionGuid() {
        String trxGuidPrifix = "A";
        return trxGuidPrifix + systemPrefix() +"SG" +DateUtils.getMillisecTime() + occrRmno(2) + occrSqnc3(2);
    }

    /**
     * 회원번호 생성
     * Statements
     * 회원번호 생성규칙
  	 * 가입경로(1) + systemPrefix(2) + YYMMDDHH(8) + random(6) 영문+숫자 조합 + 시퀀스(3)
     * @return
     */
    public String createUserNo(String preFix) {

        return preFix + systemPrefix() + DateUtils.getTimeYYMMDDHH() + occrRandomNumChar(6) + occrSqnc4(3);
    }




    /**
     *
     * 시스템서버구분 반환
     *
     * @return
     */
    public static String systemPrefix(){

        /*
         * 서버명 bppay_api01, bppay_api02...
         * 컨테이너명 bppay_api01_01, bppay_api01_01 ...
         * 반환값 11, 12, 21, 22....
         */
        StringBuffer sb = new StringBuffer();
        //서버
        if(System.getProperties().toString().indexOf("bppay_api01") != -1){
            sb.append("2");
        }
        else {
            sb.append("1");
        }

        //컨테이너
        if(System.getProperties().toString().indexOf("bppay_api01_02") != -1 || System.getProperties().toString().indexOf("bppay_api02_02") != -1){
            sb.append("2");
        }
        else {
            sb.append("1");
        }

        return sb.toString();
    }

    /**
     *
     * 해당자릿수의 난수발생
     * 예) occrRmno(3) 이면 "123" 반환, occrRmno(5) "00218"
     *
     * @param i
     * @return
     */
    public static String occrRmno(int i){
        Random random = new Random();
        random.setSeed(new Date().getTime());

        int num = random.nextInt((int)Math.pow(10, i));
        String strN = String.valueOf(num);
        if(strN.length() > i){
            strN = strN.substring(0,i);
        }else{
            strN = StringUtils.rightPad(strN, i, "0");
        }
        return strN;
    }

    /*trx_guid 용*/
    public static int sqnc = 0;
    public static synchronized String occrSqnc(int i){
        if(sqnc > 90){
            sqnc = 0;
        }
        else{
            sqnc++;
        }
        return StringUtils.leftPad(String.valueOf(sqnc), i, "0");
    }

    /*내부대체식별번호 용*/
    public static int sqnc2 = 0;
    public static synchronized String occrSqnc2(int i){
        if(sqnc2 > 90){
            sqnc2 = 0;
        }
        else{
            sqnc2++;
        }
        return StringUtils.leftPad(String.valueOf(sqnc2), i, "0");
    }

    /*세션GUID 용*/
    public static int sqnc3 = 0;
    public static synchronized String occrSqnc3(int i){
        if(sqnc3 > 90){
            sqnc3 = 0;
        }
        else{
            sqnc3++;
        }
        return StringUtils.leftPad(String.valueOf(sqnc3), i, "0");
    }

    /* 회원번호 용*/
    public static int sqnc4 = 0;
    public static synchronized String occrSqnc4(int i){
        if(sqnc4 > 990){
            sqnc4 = 0;
        }
        else{
            sqnc4++;
        }
        return StringUtils.leftPad(String.valueOf(sqnc4), i, "0");
    }

    /**
     * 회원번호 생성용
     * 입력받은 자릿수 만큼 영문+숫자 랜덤값 리턴
     */
    public static synchronized String occrRandomNumChar(int z){
    	Random rnd =new Random();
    	StringBuffer buf =new StringBuffer();

    	for(int y=0; y<z; y++){
    	    //rnd.nextBoolean() 는 랜덤으로 true, false 를 리턴. true일 시 랜덤 한 대문자를, false 일 시 랜덤 한 숫자를 StringBuffer 에 append 한다.
    	    if(rnd.nextBoolean()){
    	        buf.append((char)((int)(rnd.nextInt(26))+65));
    	    }else{
    	        buf.append((rnd.nextInt(10)));
    	    }
    	}

        return buf.toString();
    }

    /**
    *
    * 내부대체식별번호추출
    * A+C/B/T + YYYYMMDDHH25MIssSSS+난수(3) + 시퀀스(2) = 24자리
    * @param innrTrfrIdnoTp 내부대체식별번호추출구분
    * @return
    */
    public String getInnrTrfrIdno(String innrTrfrIdnoTp) {
       return innrTrfrIdnoTp + DateUtils.getMillisecTime() + occrRmno(3) + occrSqnc2(2);
    }
}
