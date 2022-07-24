package com.basic.common.utils.crypto;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.basic.common.utils.StringUtils;

public class AES128 {
protected Logger logger = LoggerFactory.getLogger(getClass());

    private String hashKey;
    private byte[] ivBytes;
    private Cipher cipher;

    public AES128(String hashKey, String iv) {
        try {
        	this.hashKey = hashKey;
            this.ivBytes = iv.getBytes();

            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] privateKey = new byte[16];
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            privateKey = initialPrivateKey();

            SecretKeySpec keySpec = new SecretKeySpec(privateKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        } catch (Exception e) {
            logger.error("▷▷AESCryptUtil Exception : hashKey = {} ", hashKey);
        }
    }

    /**
     * 평문을 암호화한다.
     *
     * @param pbPlainData 결제관련 전문
     * @param bSessionKey 암호화에 사용될 세션키
     * @return 암호화된 결제관련 전문
     * @throws Exception
     */
    public String encrypt(String plainText) {
    	logger.info("▷▷AES128 encrypt param = {} ", plainText);

    	try {
	        byte[] privateKey = new byte[16];
	        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
	        privateKey = initialPrivateKey();

	        SecretKeySpec keySpec = new SecretKeySpec(privateKey, "AES");
	        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
	        byte[] doFinal = cipher.doFinal(plainText.getBytes());
	        String strReturn = Base64.encodeBase64String(doFinal);

	        logger.info("▷▷AES128 encrypt result = {} ", strReturn);

	        return strReturn;
    	}catch(Exception e) {
    		logger.error("▷▷AES128 encrypt Exception : input = {} ", plainText);
    	}
        return "exception";
    }

    /**
     * 암호문을 평문으로 해독
     *
     * @param encryptedValue 암호화 전문
     * @return 복호화된 결제관련 전문
     * @throws Exception
     */
    public String decrypt(String encryptedValue) {
    	logger.info("▷▷AES128 decrypt param = {} ", encryptedValue);

    	if("".equals(encryptedValue)) {
    		return "";
    	}

        byte[] plainText = Base64.decodeBase64(encryptedValue.getBytes());
        try {
            byte[] privateKey = new byte[16];
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            privateKey = initialPrivateKey();

            SecretKeySpec keySpec = new SecretKeySpec(privateKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

            String strReturn = new String(cipher.doFinal(plainText));

            logger.info("▷▷AES128 decrypt result = {} ", strReturn);

            return strReturn;
        } catch (Exception e) {
            logger.error("▷▷AES128 decrypt Exception : encryptedValue = {} ", encryptedValue);

            return "exception";
        }

    }

    /**
     * 암호문을 평문으로 해독
     *
     * @param encryptedValue 암호화 전문
     * @return 복호화된 결제관련 전문
     * @throws Exception
     */
    public String decrypt(Object encryptedValue) {
    	logger.info("▷▷AES128 decrypt param = {} ", StringUtils.nvl(encryptedValue));

    	if("".equals(StringUtils.nvl(encryptedValue, ""))) {
    		return "";
    	}

        byte[] plainText = Base64.decodeBase64(encryptedValue.toString().getBytes());
        try {
            byte[] privateKey = new byte[16];
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            privateKey = initialPrivateKey();

            SecretKeySpec keySpec = new SecretKeySpec(privateKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

            String strReturn = new String(cipher.doFinal(plainText));

            logger.info("▷▷AES128 decrypt result = {} ", strReturn);

            return strReturn;
        } catch (Exception e) {
            logger.error("▷▷decrypt Exception : encryptedValue = {} ", encryptedValue);
            return "exception";
        }

    }

    private byte[] initialPrivateKey() {
        String needKey = hashKey.substring(0, 16);

        byte[] keyBytes = new byte[16];
        byte[] b = needKey.getBytes();

        int len = b.length;

        if(len > keyBytes.length){
            len = keyBytes.length;
        }

        System.arraycopy(b, 0, keyBytes, 0, len);

        return keyBytes;
    }

}
