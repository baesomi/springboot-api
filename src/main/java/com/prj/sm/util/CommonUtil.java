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

	public static boolean isValidEmail(String chkStr) { 
		String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"; 
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(chkStr); 
		return m.matches(); 
	}
	
	public static boolean isValidPw(String chkStr) { 
		if(chkStr.length() < CommonConst.MINIMUN_PW_LENGTH) {
			return false;
		}
		String regex = "(?=^.{6,255}$)((?=.*\\d)(?=.*[A-Z])(?=.*[a-z])|(?=.*\\d)(?=.*[^A-Za-z0-9])(?=.*[a-z])|(?=.*[^A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z])|(?=.*\\d)(?=.*[A-Z])(?=.*[^A-Za-z0-9]))^.*"; 
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(chkStr); 
		return m.matches();
	}
}