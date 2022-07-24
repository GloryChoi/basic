package com.basic.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;


/**
 *
 * @ClassName   : StringUtil.java
 * @Description : StringUtils 유틸 클래스
 * @Modification Information
 * @
 * @ 수정일                       수정자                      수정내용
 * @ -----------   -----------   -------------------------------
 * @ 2020. 3. 10.  cyk       2020. 3. 10. 최초생성
 *
 * @author cyk
 * @since 2020. 3. 10.
 * @version 1.0
 * @see
 *
 *  Copyright (C) by cyk All right reserved.
 *
 *  정규식 참고.
 *  영어와 숫자만 가능: ^[a-zA-Z0-9]+$
	영어만 가능: ^[a-zA-Z]+$
	모든 ASCII 문자: [\x00-\x7F]
	10 진수 숫자만 가능: ^[0-9]+$
	소문자 영어만 가능:[a-z]
	우편번호（숫자 3자리수 - 숫자 2자리수）: ^\\d{3}-\\d{4}$
	특수 문자:!"#$%&'()*+,-./:;<=>?@[]^_`{
	공백 문자:[ \t\n\x0B\f\r]
	대문자 영어만 가능:^[A-Z]+$
	16진수 숫자만 가능:[0-9a-fA-F]
 */
public class StringUtils{

    public StringUtils() {
        super();
    }

    public static String[] split(String str, String var) {
        if(!isEmpty(str)) {
            String[] strArr = null;
            strArr = str.split(var);
            return strArr;
        } else {
            return new String[0];
        }
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

	public static String nvl(String str, String replace){
		String result = str;
		if (isEmpty(str) || str.toLowerCase().equals("null") ) {
			result = replace;
		}
		return result;
	}
	public static String nvl(Object obj, String replace){
		if (obj == null || String.valueOf(obj).trim().length() == 0 || String.valueOf(obj).toLowerCase().equals("null")) {
			return replace;
		}else{
			return String.valueOf(obj);
		}
	}
	public static String nvl(String str){
		String result = str;
		if (isEmpty(str) || str.toLowerCase().equals("null") ) {
			result = "";
		}
		return result;
	}
	public static String nvl(Object obj){
		if (obj == null || String.valueOf(obj).trim().length() == 0 || String.valueOf(obj).toLowerCase().equals("null")) {
			return "";
		}else{
			return String.valueOf(obj);
		}
	}

	public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isNullOrWhitespace(String s) {
        return s == null || isWhitespace(s);

    }

    private static boolean isWhitespace(String s) {
        int length = s.length();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                if (!Character.isWhitespace(s.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

	/**
     * underscore ('_') 가 포함되어 있는 문자열을 Camel Case로 변환
     * @name convertCamelCase
     * @param underScore : '_'가 포함된 변수명
     * @return Camel 표기법 변수명
     */
    public static String convertCamelCase(String underScore) {

        if (underScore.indexOf('_') < 0 && Character.isLowerCase(underScore.charAt(0))) {
            return underScore;
        }
        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;
        int len = underScore.length();

        for (int i = 0; i < len; i++) {
            char currentChar = underScore.charAt(i);
            if (currentChar == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(currentChar));
                    nextUpper = false;
                } else {
                    result.append(Character.toLowerCase(currentChar));
                }
            }
        }
        return result.toString();
    }


    /**
     * 휴대폰번호 Mask
     *
     */
    public static String maskMbphNo(String mbphNo) {
        String strMbphNo = String.valueOf(mbphNo);
        String maskString = String.valueOf(strMbphNo);

        Matcher matcher = Pattern.compile("^(01\\d{1})-?(\\d{3,4})-?(\\d{4})").matcher(strMbphNo);

        if (matcher.matches()) {
            maskString = "";

            for (int i = 1; i <= matcher.groupCount(); i++) {

                String maskTarget = matcher.group(i);

                if (i > 1) {
                    if(maskTarget.length() == 3){
                        maskString = maskString + maskTarget.substring(0, 1) + "**";
                    }else{
                        maskString = maskString + maskTarget.substring(0, 2) + "**";
                    }
                } else{
                    maskString = maskString + maskTarget;
                }
            }
        } else {
            maskString = strMbphNo;
        }

        return maskString;
    }

    /**
     * 휴대폰번호 Mask
     *
     */
    public static String maskMbphNoHyphen(String mbphNo) {
        String strMbphNo = String.valueOf(mbphNo);
        String maskString = String.valueOf(strMbphNo);

        Matcher matcher = Pattern.compile("^(01\\d{1})-?(\\d{3,4})-?(\\d{4})").matcher(strMbphNo);

        if (matcher.matches()) {
            maskString = "";

            for (int i = 1; i <= matcher.groupCount(); i++) {

                String maskTarget = matcher.group(i);

                if (i > 1) {
                    if(maskTarget.length() == 3){
                        maskString = maskString + maskTarget.substring(0, 1) + "**";
                    }else{
                        maskString = maskString + maskTarget.substring(0, 2) + "**";
                    }
                } else{
                    maskString = maskString + maskTarget;
                }

                if (i < matcher.groupCount()) {
                    maskString = maskString + "-";
                }
            }
        } else {
            maskString = strMbphNo;
        }

        return maskString;
    }

    /**
     * 이름 Mask
     * 2글자 김*
     * 3글자 홍*동
     * 4글자 이상 김**개
     */
    public static String maskMbNm(String name) {
        if(name == null || "".equals(name)){
            return "";
        }

        String fName = name.substring(0, 1);
        String lName = name.substring(1, name.length());

        if(lName.length() == 2){
            lName = "*"+lName.substring(1, lName.length());
        }else if(lName.length() > 2){
            lName = "**"+lName.substring(2, lName.length());
        }else{
            lName = "*";
        }

        name = fName + lName;;

        return name;
    }

    /**
     * 이메일주소 Mask
     *
     */
    public static String maskEmad(String emad) {
        String strEmad = String.valueOf(emad);
        String maskString = String.valueOf(strEmad);
        String[] email = strEmad.split("\\@");
        String pattern = "";

        if (email.length == 2) {
            if (email[0].length() > 2) {
                pattern = "^(..)(.*)([@]{1})(.*)$";
            } else if(email[0].length() == 2){
                pattern = "^(.*)(.)([@]{1})(.*)$";
            }else {
            	return maskString;
            }

            Matcher matcher = Pattern.compile(pattern).matcher(strEmad);

            if (matcher.matches()) {
                maskString = "";

                for (int i = 1; i <= matcher.groupCount(); i++) {

                    String maskTarget = matcher.group(i);

                    if (i==2) {
                        char[] c = new char[maskTarget.length()];
                        Arrays.fill(c, '*');

                        maskString = maskString + String.valueOf(c);
                    } else{
                        maskString = maskString + maskTarget;
                    }
                }
            }
        } else {
            maskString = strEmad;
        }

        return maskString;
    }

    /**
     *
     * 문자열을 길이만큼 왼쪽으로 Char 채워서 반환
     *
     * @param str
     * @param length
     * @param ch
     * @return
     */
    public static String leftPad(String str, int length, String ch){
        String tmp = str;
        if(tmp == null) tmp = "";
        while(tmp.length() < length){
            tmp = ch + tmp;
        }
        return tmp;
    }

    /**
     *
     * 문자열을 길이만큼 오른쪽에 Char 채워서 반환
     *
     * @param str
     * @param length
     * @param ch
     * @return
     */
    public static String rightPad(String str, int length, String ch) {
        String tmp = str;
        if(tmp == null) tmp = "";
        while(tmp.length() < length) {
            tmp = tmp + ch;
        }
        return tmp;
    }

    /**
     *
     * 문자열을 길이만큼 왼쪽에 0을 채워서 반환
     *
     * @param str
     * @param length
     * @param ch
     * @return
     */
    public static String fillZero(String str, int length) {
        String tmp = str;
        if (tmp == null) tmp = "";
        while (tmp.length() < length) {
            tmp = "0" + tmp;
        }
        return tmp;
    }
    /**
     *
     * String 객체가 Null 인경우 "" 반환한다.
     *
     * @param str
     * @return
     */
    public static String nullToStr(String str) {
        if( str == null ) {
            return "";
        } else {
            return str;
        }
    }

    /**
     *
     * String 객체가 Null 인경우 "" 반환하고 그외 Trim() 하고 반환한다.
     *
     * @param str
     * @return
     */
    public static String cnvNull2StrEmpty(String str){
        if(str == null) return "";
        else return str.trim();
    }

    /**
     *
     * 문자열의 NULL 혹은 빈문자열 여부를 반환한다.
     *
     * @param str 문자열
     * @return
     * NULL 혹은 빈문자열 일경우 true
     * 아닐경우 false
     */
    public static boolean isNVL(String str) {
        if( str == null || str.trim().length() == 0 ) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * 주민등록번호 7자리로 생년월일, 성별, 내외국인값 을 반환한다.
     *
     * @param rsdtNo 주민등록번호
     * @return 0번째 index : 생년월일(8자리), 1번째 인덱스 : 성별 (남자:M , 여자:W), 2번째 인덱스 : 내외국인여부 (내국인 : 0, 외국인 :1)
     */
    public static String[] getBryyMndyAndSex(String rsdtNo) {

        String[] rsltArr = new String[3];
        try {

            String rsdtBryy = rsdtNo.substring(0, 6);                        // 생년월일 6자리
            int rsdtSex = Integer.parseInt(rsdtNo.substring(6, 7));
            if( rsdtSex%2 == 0 ) {  // 여자는 짝수 이므로 0
                rsltArr[1] = "W";
            } else {    // 남자는 홀수 이므로 1
                rsltArr[1] = "M";
            }

            if( rsdtSex == 1 || rsdtSex == 2 || rsdtSex == 5 || rsdtSex == 6 ) {
                rsltArr[0] = "19" + rsdtBryy;
            } else if( rsdtSex == 3 || rsdtSex == 4 || rsdtSex == 7 || rsdtSex == 8 ) {
                rsltArr[0] = "20" + rsdtBryy;
            } else {
                rsltArr[0] = "18" + rsdtBryy;
            }

            if( rsdtSex >= 5 && rsdtSex <= 8 ) {    // 외국인
                rsltArr[2] = "1";
            } else {
                rsltArr[2] = "0";
            }
        } catch(Exception e) {
            e.getMessage();
        }

        return rsltArr;
    }

    /**
     * 주민등록번호 7번째 자리
     * 홀수 : 남성, 짝수 : 여성
     * 9,0 : 1800년대-내국인
     * 1,2 : 1900년대-내국인
     * 3,4 : 2000년대-내국인
     * 5,6 : 1900년대-외국인
     * 7,8 : 2000년대-외국인
     */
    /**
     *
     * 생년월일, 성별, 내외국인 여부로 주민등록번호 7자리를 반환한다.
     *
     * @param bryyMndy 생년월일 8자리 yyyyMMdd
     * @param sex 성별 (남자:M , 여자:W)
     * @param lnfrYn 내외국인여부 (내국인 Y, 외국인 N)
     * @return
     */
    public static String getRsdtNo(String bryyMndy, String sex, String lnfrYn) {

        String rsdtNo = "";

        try {
            if( bryyMndy.length() < 8 ) throw new Exception();

            int yyyy = Integer.parseInt(bryyMndy.substring(0, 4));

            Integer rsdtSex = null;
            if( yyyy < 1900 ) { // 1800년대
                rsdtSex = 9;
            } else if( yyyy >= 1900 && yyyy < 2000 ) {  // 1900년대
                rsdtSex = 1;
            } else if( yyyy >= 2000 ) { // 2000년대
                rsdtSex = 3;
            }

            if( "N".equals(lnfrYn) ) { rsdtSex = rsdtSex + 4; }   // 외국인인경우 4를 더해준다.

            if( "W".equals(sex) ) { rsdtSex++; }    // 여성일경우 1씩 증가
            if( rsdtSex == 10 ) { // 1800년대 여성의 경우 0이므로 10이 됬을 경우 0으로 내려준다.
                rsdtSex = 0;
            }

            // 1800년대 외국인은 없으므로 10이상이 되었을 경우 잘못된정보
            if( rsdtSex > 10 ) { throw new Exception(); }

            rsdtNo = bryyMndy.substring(2, 8) + Integer.toString(rsdtSex);
        } catch(Exception e) {
        	e.getMessage();
        }

        return rsdtNo;
    }

    /**
     * 주민등록번호 7번째 자리
     * 홀수 : 남성, 짝수 : 여성
     * 9,0 : 1800년대-내국인
     * 1,2 : 1900년대-내국인
     * 3,4 : 2000년대-내국인
     * 5,6 : 1900년대-외국인
     * 7,8 : 2000년대-외국인
     */
    /**
     *
     * 생년월일, 성별, 내외국인 여부로 주민등록번호 뒤 1자리를 반환한다.
     *
     * @param bryyMndy 생년월일 8자리 yyyyMMdd
     * @param sex 성별 (남자:M , 여자:F)
     * @param lnfrYn 내외국인여부 (내국인 Y, 외국인 N)
     * @return
     */
    public static String getRsdtNo7(String bryyMndy, String sex, String lnfrYn) {

        String rsdtNo = "";

        try {
            if( bryyMndy.length() < 8 ) throw new Exception();

            int yyyy = Integer.parseInt(bryyMndy.substring(0, 4));

            Integer rsdtSex = null;
            if( yyyy < 1900 ) { // 1800년대
                rsdtSex = 9;
            } else if( yyyy >= 1900 && yyyy < 2000 ) {  // 1900년대
                rsdtSex = 1;
            } else if( yyyy >= 2000 ) { // 2000년대
                rsdtSex = 3;
            }

            if( "N".equals(lnfrYn) ) { rsdtSex = rsdtSex + 4; }   // 외국인인경우 4를 더해준다.

            if( "F".equals(sex) ) { rsdtSex++; }    // 여성일경우 1씩 증가
            if( rsdtSex == 10 ) { // 1800년대 여성의 경우 0이므로 10이 됬을 경우 0으로 내려준다.
                rsdtSex = 0;
            }

            // 1800년대 외국인은 없으므로 10이상이 되었을 경우 잘못된정보
            if( rsdtSex > 10 ) { throw new Exception(); }

            rsdtNo = Integer.toString(rsdtSex);
        } catch(Exception e) {
        	e.getMessage();
        }

        return rsdtNo;
    }

    /**
     *
     * MAP에 담긴 Key, Value를 GET방식의URL문자열로 반환
     *
     * @param map
     * @return
     */
    public static String cnvMap2GetUrlString(Map<String, String> map){

        StringBuffer sb = new StringBuffer();
        Iterator<String> mapiter = map.keySet().iterator();

        while(mapiter.hasNext()){
            String key = cnvNull2StrEmpty(mapiter.next());
            if(!key.isEmpty()){
                if(sb.length() > 0) sb.append("&");
                sb.append(key);
                sb.append("=");
                sb.append(cnvNull2StrEmpty(map.get(key)));
            }
        }
        return sb.toString();
    }

    /**
     *
     * GET방식의URL문자열을 구분자로 나뉘서 MAP 반환
     *
     * @param str
     * @return
     */
    public static Map<String, String> cnvGetUrlString2Map(String str){

        String ch1 = "&amp;";
        String ch2 = "=";
        Map<String, String> data = null;

        if(!isNVL(str)){
            data = new HashMap<String, String>();
            String arr[] = str.split(ch1);
            if(arr.length > 0){
                int idx = -1;
                for(String tmp : arr){
                    idx = tmp.indexOf(ch2);
                    if(idx > 0){
                        if(tmp.length() > idx+1){
                            data.put(tmp.substring(0, idx), tmp.substring(idx+1));
                        }
                        else{
                            data.put(tmp.substring(0, idx), "");
                        }
                    }
                    else{
                        data.put(tmp, "");
                    }
                }
            }
        }
        return data;
    }

    /**
     * 숫자 천단위 콤마(,) 찍기
     *
     */
    public static String setComma(int num) {

        DecimalFormat Comma = new DecimalFormat("#,###");

        String retrun = Comma.format(num);

        return retrun;
    }

    /**
     * 숫자 천단위 콤마(,) 찍기
     *
     */
    public static String setComma(String num) {

        DecimalFormat Comma = new DecimalFormat("#,###");

        String retrun = Comma.format(Integer.parseInt(num));

        return retrun;
    }

    public static String[] telNumSpliter(String telNum){

        Pattern telPattern = Pattern.compile("^(01\\d{1})-?(\\d{3,4})-?(\\d{4})");

        if(telNum == null){
            return new String[]{"","",""};
        }else{
            Matcher matcher = telPattern.matcher(telNum);

            if(matcher.matches()){
                return new String[]{matcher.group(1), matcher.group(2),matcher.group(3)};
            }else{
                if(telNum.length() == 11){
                    String telNum1 = telNum.substring(0, 3);
                    String telNum2 = telNum.substring(3, 7);
                    String telNum3 = telNum.substring(7, 11);

                    return new String[]{telNum1,telNum2,telNum3};
                }else{
                    String telNum1 = telNum.substring(0, 3);
                    String telNum2 = telNum.substring(3, 6);
                    String telNum3 = telNum.substring(6, 10);

                    return new String[]{telNum1,telNum2,telNum3};
                }
            }
        }
    }

    /**
     *
     * 만원, 천원 단위로 변경
     * @param amt 금액
     * @returns 만원, 천원 단위로 변경된 금액
     *
     */
    public static String toMoneyKor(String amt){
        if(amt.equals("")) return "0원";

        String rtnAmt = "";

        int temp1 = Integer.parseInt(amt);

        if(temp1 % 10000 != 0){
            rtnAmt = (int)Math.floor(temp1/10000) + "만" + (int)Math.floor((temp1 % 10000)/1000) + "천원";
        }else{
            rtnAmt = temp1/10000 + "만원";
        }

        return rtnAmt;
    }

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

    /**
     * 차대번호 Mask
     *
     */
    public static String maskCbNo(String cbno) {

        if(cbno == null || "".equals(cbno)){
            return "";
        }

        String fNo = cbno.substring(0,3);
        String lNo = cbno.substring(9, cbno.length());

        cbno = fNo + "******" + lNo;

        return cbno;
    }

    public static String getError(Exception e){
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));

        return errors.toString();
    }

    /**
     * ID 규칙 체크
     *
     */
    public static Boolean checkPatternID(String cmClasCd, String cmId) {
        Boolean result = false;
        String strCmClasCd = String.valueOf(cmClasCd);
        String strId = String.valueOf(cmId);
        String idRegExp = "";
        Pattern pattern = null;

        switch (strCmClasCd) {
            case "B":
                idRegExp = "^\\d{7}$";
                break;
            case "O":
                idRegExp = "^\\d{7}$";
                break;
            case "D":
                idRegExp = "^(D\\d{6}$)";
                break;
            default:
                idRegExp = "";
                break;
        }

        pattern = Pattern.compile(idRegExp);

        Matcher matcher;
        matcher = pattern.matcher(strId);

        if (matcher.matches()) {
            result = true;
        }

        return result;
    }

    /**
     * 비밀번호 규칙 체크
     *
     */
    public static Boolean checkPatternPWD(String pwd) {
        Boolean result = false;
        String strPwd = String.valueOf(pwd);
        String idRegExp = "^(?=.*[a-zA-Z])(?=.*[~`!@#$%\\^&*()+=\\-_|\\{\\}\\[\\]:;\'\"<>,.\\/?])(?=.*[\\d]).{8,20}$";
        Pattern pattern = null;
        pattern = Pattern.compile(idRegExp);

        Matcher matcher;
        matcher = pattern.matcher(strPwd);

        if (matcher.matches()) {
            result = true;
        }

        return result;
    }

    /**
     * 숫자 체크
     *
     */
    public static Boolean checkNumber(String param) {
        Boolean result = false;
        param = nvl(param);
        String idRegExp = "^[0-9]+$";
        Pattern pattern = Pattern.compile(idRegExp);

        Matcher matcher;
        matcher = pattern.matcher(param);

        if (matcher.matches()) {
            result = true;
        }

        return result;
    }

    /**
     * @param param
     * @return
     * @description 빈값이면 "" 빈값이 아니면 base64Encode 값 return
     */
    public static String base64Encode(String param) {
    	String result = "";

    	if("".equals(nvl(param, ""))) {
    		return result;
    	}else {
    		result = Base64.encodeBase64URLSafeString(param.getBytes());
    	}

    	return result;
    }

    /**
     * @param param
     * @return
     * @description 빈값이면 "" 빈값이 아니면 base64Encode 값 return
     */
    public static String base64Encode(Object obj) {
    	String result = "";

    	if("".equals(nvl(obj, ""))) {
    		return result;
    	}else {
    		result = Base64.encodeBase64String(obj.toString().getBytes());
    	}

    	return result;
    }

    /**
     * @param param
     * @return
     * @description 빈값이면 "" 빈값이 아니면 base64decode 값 return
     */
    public static String base64Decode(String param) {
    	String result = "";

    	if("".equals(param)) {
    		return result;
    	}else {
    		result = Base64.decodeBase64(param.getBytes()).toString();
    	}

    	return result;
    }


    /**
     * @param param
     * @return
     * @description 빈값이면 "" 빈값이 아니면 URLencode 값 return
     */
    public static String URLEncode(String param) {
    	String result = "";

    	if("".equals(nvl(param, ""))) {
    		return result;
    	}else {
    		try {
    			result = URLEncoder.encode(param, "UTF-8");
    		}catch(Exception e) {
    			getError(e);
    		}
    	}

    	return result;
    }

    /**
     * @param param
     * @return
     * @description 빈값이면 "" 빈값이 아니면 URLdecode 값 return
     */
    public static String URLEncode(Object obj) {
    	String result = "";

    	if("".equals(nvl(obj, ""))) {
    		return result;
    	}else {
    		try {
    			result = URLEncoder.encode(obj.toString(), "UTF-8");
    		}catch(Exception e) {
    			getError(e);
    		}
    	}

    	return result;
    }

    /**
     * @param param
     * @return
     * @description 빈값이면 "" 빈값이 아니면 URLdecode 값 return
     */
    public static String URLDecode(String param) {
    	String result = "";

    	if("".equals(nvl(param, ""))) {
    		return result;
    	}else {
    		try {
    			result = URLDecoder.decode(param, "UTF-8");
    		}catch(Exception e) {
    			getError(e);
    		}
    	}

    	return result;
    }

    /**
     * @param param
     * @return
     * @description 빈값이면 "" 빈값이 아니면 URLdecode 값 return
     */
    public static String URLDecode(Object obj) {
    	String result = "";

    	if("".equals(nvl(obj, ""))) {
    		return result;
    	}else {
    		try {
    			result = URLDecoder.decode(obj.toString(), "UTF-8");
    		}catch(Exception e) {
    			getError(e);
    		}
    	}

    	return result;
    }

    public static Map<String, String> convertNvpToMap(String str){
        Map<String, String> map = new HashMap<String, String>();

        if(str == null || "".equals(str)){
            return null;
        }

        String[] array = str.split("&");

        for(int i = 0; i < array.length; i++){
            String[] param = array[i].split("=");
            if(param.length > 1){
                String name = param[0].trim();
                String value = param[1].trim();

                map.put(name, value);
            }else{
                if(param.length == 1) {
                	String name = param[0].trim();
                    map.put(name, "");
                }
            }
        }

        return map;
    }

    @SuppressWarnings("unchecked")
	public static JSONObject convertNvpToJson(String str){
        JSONObject object = new JSONObject();

        if(str == null || "".equals(str)){
            return null;
        }

        String[] array = str.split("&");

        for(int i = 0; i < array.length; i++){
            String[] param = array[i].split("=");
            if(param.length > 1){
                String name = param[0].trim();
                String value = param[1].trim();

                object.put(name, value);
            }else{
                if(param.length == 1) {
                	String name = param[0].trim();
                    object.put(name, "");
                }
            }
        }

        return object;
    }
}

