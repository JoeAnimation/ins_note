package com.sicau.ins_note.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;

import com.sicau.ins_note.enums.ResultEnum;
import com.sicau.ins_note.exception.NoteException;

import lombok.extern.slf4j.Slf4j;

public class NoteUtil {
	public static String createId(){
		UUID uuid = UUID.randomUUID();
//		String id = uuid.toString();
//		return id.replace("-","");
		return uuid.toString().replace("-","");
	}
	/**
	 * 将传入的src加密处理
	 * @param src 明文字符串
	 * @return 加密后的字符串结果
	 */
	public static String md5(String src){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			//MD5加密处理
			byte[] output = md.digest(src.getBytes());
			//Base64加密处理
			String str = Base64.encodeBase64String(output);
			return str.toString().replace("==", "");
		} catch (NoSuchAlgorithmException e) {
			throw new NoteException(ResultEnum.FAILURE);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("lyx1711通过md5加密:"+md5("lyx1711"));
		System.out.println(md5("lyx1711").length());
		System.out.println("UUID:"+createId());
		System.out.println(md5(createId()));
		System.out.println(createId().length());
	}
}

