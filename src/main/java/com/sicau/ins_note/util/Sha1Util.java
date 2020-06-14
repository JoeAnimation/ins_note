package com.sicau.ins_note.util;

import java.security.MessageDigest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Sha1Util {
	/**
     * SHA1加密
     * @param str
     * @return
     */
    public static String string2Sha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
 
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            log.error("e",e);
            return null;
        }
    }
    
    public static void main(String[] args) {
    	Sha1Util sha1Util = new Sha1Util();
		String pwd="lyx1711";
		System.out.println(sha1Util.string2Sha1(pwd));
	}
    
}



