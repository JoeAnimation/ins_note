package com.sicau.ins_note.util;

import org.mindrot.jbcrypt.BCrypt;

public abstract class BcryptUtil {

	public static String bcrypt(String src){
		String  originalPassword = src;
		String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
		
		return generatedSecuredPasswordHash;
	}
	public static void main(String[] args){
		String originalPassword="lyx1711";
		
		String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
		System.out.println(generatedSecuredPasswordHash);
 
        boolean matched = BCrypt.checkpw(originalPassword, generatedSecuredPasswordHash);
 
        System.out.println(matched);
        
	}

}
