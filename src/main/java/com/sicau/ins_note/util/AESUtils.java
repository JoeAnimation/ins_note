package com.sicau.ins_note.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * AES工具类
 * @author Administrator
 *
 */
@Slf4j
public class AESUtils {

	private final static String encodeRules = "sicau";
	
	/**
	 * 加密
	 * 1、构造密钥生成器
	 * 2、根据encodeRules规则初始化密钥生成器
	 * 3、产生密钥
	 * 4、创建和初始化密码器
	 * 5、内容加密
	 * 6、返回字符串
	 */
	public static String AESEncode(String content){
		try {
			//1、构造密钥生成器，指定为AES算法，不区分大小写
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			//2、根据encodeRules规则初始化密钥生成器
			//根据传入的字节数组，生成一个128位的随机源
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(encodeRules.getBytes());
			keyGenerator.init(128, random);
			//3、产生原始对称密钥
			SecretKey originalKey = keyGenerator.generateKey();
			//4、获取原始对称密钥字节数组
			byte[] raw = originalKey.getEncoded();
			//5、根据字节数组生成AES密钥
			SecretKey key = new SecretKeySpec(raw, "AES");
			//6、根据指定算法AES生成密码器
			Cipher cipher = Cipher.getInstance("AES");
			//7、初始化密码器，第一个参数为(Encrypt_mode)或解密(Dcrypt_mode)操作，第二个参数为使用的KEY
			cipher.init(Cipher.ENCRYPT_MODE, key);
			//8.获取加密内容的字节数组
			byte[] byteEncode = content.getBytes("utf-8");
			//9、根据密码器的初始化方式--加密：将数据加密
			byte[] byteAES = cipher.doFinal(byteEncode);
			//10、将加密后的数据转换为字符串
			//String AESEncode = new String(new BASE64Encoder().encode(byteAES));
			String AESEncode = Base64.getEncoder().encodeToString(byteAES);
			//11、将字符串返回
			return AESEncode.replace("=", "");
		} catch (Exception e) {
			if (e.getMessage() != null) {
				log.error("error msg: {}",e.getMessage());
			}
		}
		//如果有错就返回null
		return null;
	}

	/** 
     * AES加密 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的byte[] 
	 * @throws Exception 
     * @throws Exception 
     */ 
	public byte[] AESEncryptToBytes(String content,String encryptKey) throws Exception{
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128, new SecureRandom(encryptKey.getBytes()));
		
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyGenerator.generateKey().getEncoded(), "AES"));
		
		return cipher.doFinal(content.getBytes("utf-8"));
	}
	
	 /**
     * 解密
     * 解密过程：
     * 1.同加密1-4步
     * 2.将加密后的字符串反解成byte[]数组
     * 3.将加密内容解密
     */
	public static String AESDecode(String content){
		try {
			//1、构造密钥生成器，指定为AES算法
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			//2、根据encodeRules 规则初始化密钥生成器
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(encodeRules.getBytes());
			keyGen.init(128, random);
			//3、产生原始对称密钥
			SecretKey originKey = keyGen.generateKey();
			byte[] raw = originKey.getEncoded();
			//5、根据字节数组生成AES密钥
			SecretKey key = new SecretKeySpec(raw, "AES");
			//6、根据指定算法AES生成密码器
			Cipher cipher = Cipher.getInstance("AES");
			//7、初始化密码器，第一个参数为加密(Encrypt_mode)或解密(Decrypt_mode)，第二个参数为使用的KEY
			cipher.init(Cipher.DECRYPT_MODE, key);
			//8、将加密并编码后的内容解码成字节数组
			//byte[] byte_content = new BASE64Decoder().decodeBuffer(content);
			byte[] byteContent = Base64.getDecoder().decode(content);
			
			//解密
			byte[] byteDecode = cipher.doFinal(byteContent);
			String AESDecode = new String(byteDecode, "utf-8");
			return AESDecode;
		} catch (Exception e) {
			if (e.getMessage() != null) {
				log.error("error msg: {}", e.getMessage());
			}
			
		}
		//有错则返回null
		return null;
	}
	
	/** 
     * AES解密 
     * @param encryptBytes 待解密的byte[] 
     * @param decryptKey 解密密钥 
     * @return 解密后的String 
     * @throws Exception 
     */

	public String AESDecryptByBytes(byte[] encryptBytes, String decryptKey){
		KeyGenerator keyGenerator;
		try {
			keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128, new SecureRandom(decryptKey.getBytes()));
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyGenerator.generateKey().getEncoded(),  "AES"));
			byte[] decryptBytes = cipher.doFinal(encryptBytes);
			
			return new String(decryptBytes);
		} catch (Exception e) {
			if (e.getMessage() != null) {
				log.error("error msg: {}",e.getMessage());
			}
		}
	    //错误则返回 null
		return null;
	}
	
	 /** 
     * base 64 加密 
     * @param bytes 待编码的byte[] 
     * @return 编码后的base 64 code 
     */ 
	public String base64Encode(byte[] bytes){
		//return new BASE64Encoder().encode(bytes);
		return Base64.getEncoder().encodeToString(bytes);
	}
	
	  /** 
     * base 64 解密 
     * @param base64Code 待解码的base 64 code 
     * @return 解码后的byte[] 
     * @throws Exception 
     */ 
	public byte[] base64Decode(String base64Code){
		//return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
		return StringUtils.isEmpty(base64Code) ? null : Base64.getDecoder().decode(base64Code);
	}
	
	  /** 
     * 将base 64 code AES解密 
     * @param encryptStr 待解密的base 64 code 
     * @param decryptKey 解密密钥 
     * @return 解密后的string 
     * @throws Exception 
     */ 

	public String AESDecrypt(String encryptStr, String decryptKey){
		 return StringUtils.isEmpty(encryptStr) ? null : AESDecryptByBytes(base64Decode(encryptStr), decryptKey); 
	}
	
	 /** 
     * AES加密为base 64 code 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的base 64 code 
     * @throws Exception 
     */
	public String AESEncrypt(String content, String encryptKey) throws Exception {  
        return base64Encode(AESEncryptToBytes(content, encryptKey));  
    }
	
	public static void main(String[] args) {
		 String[] keys = {
	                "", "root"
	        };
	        System.out.println("key | AESEncode | AESDecode");
	        for (String key : keys) {
	            System.out.print("key:"+key + " | ");
	            String encryptString = AESEncode(key);
	            System.out.print(encryptString + " | ");
	            String decryptString = AESDecode(encryptString);
	            System.out.println(decryptString);
	        }
	}

}
