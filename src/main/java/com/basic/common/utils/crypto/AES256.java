package com.basic.common.utils.crypto;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.basic.common.utils.StringUtils;

public class AES256 {
protected Logger logger = LoggerFactory.getLogger(getClass());

    private String hashKey;
    private Cipher cipher;

    public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

    public AES256(String hashKey) {
        this.hashKey = hashKey;

        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] privateKey = new byte[32];
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            privateKey = initialPrivateKey();

            SecretKeySpec keySpec = new SecretKeySpec(privateKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        } catch (Exception e) {
            logger.error("▷▷AES256 Exception : hashKey = {} ", hashKey);
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
    	if("".equals(StringUtils.nvl(plainText))) {
    		return "";
    	}

        byte[] privateKey = new byte[32];
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        privateKey = initialPrivateKey();

        SecretKeySpec keySpec = new SecretKeySpec(privateKey, "AES");
        try {
        	cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        	byte[] doFinal = cipher.doFinal(plainText.getBytes());

        	return Base64.encodeBase64URLSafeString(doFinal);
        }catch(Exception e) {
        	logger.error("▷▷AES256 encrypt Exception : Value = {} ", plainText);
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
    public String decrypt(String encryptedValue) {
    	if("".equals(StringUtils.nvl(encryptedValue))) {
    		return "";
    	}

        byte[] plainText = Base64.decodeBase64(encryptedValue.getBytes());
        try {
            byte[] privateKey = new byte[32];
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            privateKey = initialPrivateKey();

            SecretKeySpec keySpec = new SecretKeySpec(privateKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return new String(cipher.doFinal(plainText)).replaceAll(" ", "+");
        } catch (Exception e) {
            logger.error("▷▷AES256 decrypt Exception : encryptedValue = {} ", encryptedValue);
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
    	if("".equals(StringUtils.nvl(encryptedValue))) {
    		return "";
    	}

        byte[] plainText = Base64.decodeBase64(encryptedValue.toString().getBytes());
        try {
            byte[] privateKey = new byte[32];
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            privateKey = initialPrivateKey();

            SecretKeySpec keySpec = new SecretKeySpec(privateKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return new String(cipher.doFinal(plainText));
        } catch (Exception e) {
            logger.error("▷▷AES256 decrypt Exception : encryptedValue = {} ", encryptedValue);
            return "exception";
        }
    }

    private byte[] initialPrivateKey() {
        String needKey = hashKey.substring(0, 32);

        byte[] keyBytes = new byte[32];
        byte[] b = needKey.getBytes();

        int len = b.length;

        if(len > keyBytes.length){
            len = keyBytes.length;
        }

        System.arraycopy(b, 0, keyBytes, 0, len);

        return keyBytes;
    }

}
