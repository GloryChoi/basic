package com.basic.common.utils.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SHA256 {
	protected Logger logger = LoggerFactory.getLogger(getClass());

    public String doCryption(String input) {
    	logger.debug("▷▷SHA256.doCryption input param : {}", input);

        if(input == null || input.equals("")) {
            return input;
        }

        StringBuffer sb = new StringBuffer();
        StringBuffer hexString = new StringBuffer();

        try{

            MessageDigest sh = MessageDigest.getInstance("SHA-256");

            sh.update(input.getBytes());

            byte byteData[] = sh.digest();

            for(int i = 0; i < byteData.length; i++){
                sb.append(Integer.toString((byteData[i]&0xff)+0x100,16).substring(1));
            }

            for(int i = 0; i < byteData.length; i++){
                String hex = Integer.toHexString(0xff&byteData[i]);

                if(hex.length() == 1){
                    hexString.append('0');
                }
                hexString.append(hex);
            }

        }catch(NoSuchAlgorithmException e){
            hexString = null;
        }

        logger.debug("▷▷SHA256.doCryption result param : {}", hexString.toString());
        return hexString.toString();
    }
}
