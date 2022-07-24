package com.basic.common.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import com.basic.common.SessionManager;
import com.basic.common.cnst.Const;

@Service
public class MessageUtils {

    @Autowired MessageSource messageSource;

    public String getMessage(String code) throws NoSuchMessageException, Exception {
        final String localeSet = StringUtils.nvl(SessionManager.getCdLng(), Const.KO);

        return messageSource.getMessage(code, null, new Locale(localeSet));
    }

    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        return messageSource.getMessage(code, args, locale);
    }

    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        return messageSource.getMessage(resolvable, locale);
    }

    public String getCode(String code) {
        final String localeSet = StringUtils.nvl(SessionManager.getCdLng(), Const.KO);
        String[] msg = messageSource.getMessage(code, null, new Locale(localeSet)).split("\\|");

        return msg[1];
    }

    public String getMsg(String code) {
        final String localeSet = StringUtils.nvl(SessionManager.getCdLng(), Const.KO);
        String[] msg = messageSource.getMessage(code, null, new Locale(localeSet)).split("\\|");

//        return StringUtils.URLEncode(msg[0]);
        return msg[0];
    }

    public String getMsg(String code, String msg2) {
        final String localeSet = StringUtils.nvl(SessionManager.getCdLng(), Const.KO);
        String[] msg = messageSource.getMessage(code, null, new Locale(localeSet)).split("\\|");

//        return StringUtils.URLEncode(msg[0]+msg2);
        return msg[0]+msg2;
    }
}
