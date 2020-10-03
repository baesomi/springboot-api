package com.prj.sm.util;

public class CommonUtil {

	public boolean isSuccess(int rc) {
		return rc == CommonConst.SUCCESS;
	}
	
	public boolean isFail(int rc) {
		return rc != CommonConst.SUCCESS;
	}
}
