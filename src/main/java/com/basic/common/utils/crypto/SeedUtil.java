package com.basic.common.utils.crypto;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SeedUtil {
	protected Logger logger = LoggerFactory.getLogger(getClass());

    public String encrypt(String seedkey, String seediv, String input) {
    	logger.debug("▷▷seed.encrypt input = {}", input);

        String result = null;

        if(input == null || input.equals("")) {
            return "";
        }

        byte[] seedkeyB = Base64.decodeBase64(seedkey);

        byte[] byteResult = KISA_SEED_CBC.SEED_CBC_Encrypt(seedkeyB, seediv.getBytes(), input.getBytes(), 0, input.length());

        result = Base64.encodeBase64String(byteResult);

        logger.debug("▷▷seed.encrypt result = {}", result);
        return result;
    }

    public String decrypt(String seedkey, String seediv, String input) {
    	logger.debug("▷▷seed.decrypt input = {}", input);

        String result = null;

        if(input == null || input.equals("")) {
            return "";
        }

        byte[] inputByte = Base64.decodeBase64(input);

        byte[] seedkeyB = Base64.decodeBase64(seedkey);

        byte[] byteResult = KISA_SEED_CBC.SEED_CBC_Decrypt(seedkeyB, seediv.getBytes(), inputByte, 0, inputByte.length);

        result = new String(byteResult);

        logger.debug("▷▷seed.decrypt result = {}", result);
        return result;
    }


}
