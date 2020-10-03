package com.prj.sm.service;

import com.prj.sm.model.Member;

public interface MemberService {
	
    int joinMember(Member member);
    int loginMember(String id, String password) throws Exception;
    
}