package com.znph.core.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Encrypts {

	public static String md5(File file) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			InputStream inputStream = new FileInputStream(file);
			byte[] buffer = new byte[1024 * 4];
			int length = 0;
			while ((length = inputStream.read(buffer)) > 0) {
				digest.update(buffer, 0, length);
			}
			inputStream.close();
			return Strings.bytesToHex(digest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String md5(String string) {
		return md5(string.getBytes());
	}

	public static String md5(byte[] bytes) {
		return Strings.bytesToHex(digest("MD5", bytes));
	}

	public static String SHA1(String string) {
		return SHA1(string.getBytes());
	}

	public static String SHA1(byte[] bytes) {
		return Strings.bytesToHex(digest("SHA-1", bytes));
	}

	public static String SHA256(String string) {
		return SHA256(string.getBytes());
	}

	public static String SHA256(byte[] bytes) {
		return Strings.bytesToHex(digest("SHA-256", bytes));
	}

	public static byte[] digest(String algorithm, byte[] data) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			digest.update(data);
			return digest.digest();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] hmacSHA1(byte[] key, byte[] data) {
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);
			return mac.doFinal(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] base64Encode(String source) {
		return base64Encode(source.getBytes());
	}

	public static byte[] base64Encode(byte[] source) {
		return Base64.getEncoder().encode(source);
	}

	public static byte[] base64Decode(String source) {
		return Base64.getDecoder().decode(source);
	}
	
	public static byte[] base64Decode(byte[] source) {
		return Base64.getDecoder().decode(source);
	}
	
	
	private static final String defaultSecretKey = "default_secret_key"; //默认密钥
 

    /**
     * 加密 (逻辑:使用加密器(Cipher)的doFinal方法进行加密, 返回字节数组转换成十六进制的字符串)
     * @param strIn 要加密的字符串
     * @return 返回加密后的十六进制字符串
     * @throws Exception
     */
    public static String encrypt(String strIn) throws Exception {

        return encrypt(strIn,defaultSecretKey);
    }
    
    
    public static String encrypt(String strIn,String key) throws Exception {
    	
    	Key skey = getKey(key.getBytes());
    	//加密器
    	Cipher encryptCipher = Cipher.getInstance("DES"); 
    	encryptCipher.init(Cipher.ENCRYPT_MODE, skey);
    	
        return Strings.bytesToHex(encryptCipher.doFinal(strIn.getBytes()));
    }
    
    /**
     * 解密 (逻辑: 把加密后的十六进制字符串转换成字节数组并使用加密器(Cipher)的doFinal方法进行解密)
     * @param strIn
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptString) throws Exception {
    	
        return decrypt(encryptString,defaultSecretKey);
    }
    
    public static String decrypt(String encryptString,String key) throws Exception {
    	
    	Key skey = getKey(key.getBytes());
    	//解密器
    	Cipher decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, skey);
        return new String(decryptCipher.doFinal(Strings.hexToBytes(encryptString)));
    }


    private static Key getKey(byte[] arrBTmp) throws Exception {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];
        // 将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        // 生成密钥
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
        return key;
    }


	
	
	
	
	
	

}
