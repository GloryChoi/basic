package com.basic.common.cnst;

/**
 *
 * @ClassName   : Const.java
 * @Description : 업무에 필요한 상수를 정의하는 클래스
 * @Modification Information
 * @
 * @ 수정일                       수정자                      수정내용
 * @ -----------   -----------   -------------------------------
 * @ 2020. 3. 20.  cyk       2020. 3. 20. 최초생성
 *
 * @author cyk
 * @since 2020. 3. 20.
 * @version 1.0
 * @see
 *
 *  Copyright (C) by cyk All right reserved.
 */
public final class Const {

    /** 개발환경설정 default,dev,stage,prd **/
    public static String ENV_PROFILE = "spring.profiles.active";
    public static String ENV_DEFAULT = "default";
    public static String ENV_DEV = "dev";
    public static String ENV_STAGE = "stage";
    public static String ENV_PRD = "prd";

    public static String CD_PAY_TYPE_P0 = "P0";
    public static String CD_PAY_TYPE_D0 = "D0";
    public static String CD_PAY_TYPE_PD = "PD";

    /**
     * KO
     */
    public static final String KO = "KO";

    /**
     * JA
     */
    public static final String JA = "JA";

    /**
     * EN
     */
    public static final String EN = "EN";

    /**
     * Y
     */
    public static final String Y = "Y";

    /**
     * N
     */
    public static final String N = "N";


    /**
     * 편집모드
     */
    public static final String MODE_INSERT = "I";
    public static final String MODE_UPDATE = "U";

    /**
     * TRUE
     */
    public static final boolean TRUE = true;

    /**
     * FALSE
     */
    public static final boolean FALSE = false;

}