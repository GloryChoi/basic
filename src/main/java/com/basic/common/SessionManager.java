package com.basic.common;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.basic.common.cnst.Const;
import com.basic.common.utils.StringUtils;

/**
 *
 * @ClassName   : SessionManager.java
 * @Description : BPSample Controller Class
 * @Modification Information
 * @
 * @ 수정일                       수정자                      수정내용
 * @ -----------   -----------   -------------------------------
 * @ 2020. 3. 10.  cyk         2020. 3. 10. 최초생성
 *
 * @author cyk
 * @since 2020. 3. 10.
 * @version 1.0
 * @see
 *
 *  Copyright (C) by cyk All right reserved.
 */
public class SessionManager {

    public SessionManager() {
        super();
    }

    /**
     * 언어셋 가져오기
     * @name getCdLng
     * @return
     * @throws Exception
     */
    public static String getCdLng() {
        return StringUtils.nvl(String.valueOf(RequestContextHolder.getRequestAttributes().getAttribute("cdLng", RequestAttributes.SCOPE_SESSION)), Const.KO);
    }

}
