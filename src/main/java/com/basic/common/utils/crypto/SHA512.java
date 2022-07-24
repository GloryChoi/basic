package com.basic.common.utils.crypto;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SHA512 {
	protected Logger logger = LoggerFactory.getLogger(getClass());

    public String doCryption(String input) {
    	logger.debug("▷▷SHA512.doCryption input param : {}", input);

        if(input == null || input.equals("")) {
            return input;
        }

        String strReturn = "";

        try{
        	MessageDigest digest = MessageDigest.getInstance("SHA-512");
		    digest.reset();
		    digest.update(input.getBytes("utf8"));
		    strReturn = String.format("%0128x", new java.math.BigInteger(1, digest.digest()));
        }catch(Exception e){
        	e.printStackTrace();
        }

        logger.debug("▷▷SHA512.doCryption result param : {}", strReturn);
        return strReturn;
    }
}
