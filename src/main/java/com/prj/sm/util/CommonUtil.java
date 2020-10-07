package com.prj.sm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

	public static boolean isSuccess(int rc) {
		return rc == CommonConst.SUCCESS;
	}
	
	public static boolean isFail(int rc) {
		return rc != CommonConst.SUCCESS;
	}
	
	public static boolean isValidEmail(String email) { 
		String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"; 
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(email); 
		return m.matches(); 
	}
	
	public static boolean isValidPW(String password) { 
		boolean err = false; 
		String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"; 
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(password); 
		if(m.matches()) { 
			err = true; 
		} 
		return err; 
	}
}
