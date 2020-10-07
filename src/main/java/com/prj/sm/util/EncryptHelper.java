package com.prj.sm.util;

public interface EncryptHelper {
	String encrypt(String password);
	boolean isMatch(String password, String hashed);
}
