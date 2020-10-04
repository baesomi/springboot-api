package com.prj.sm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.prj.sm.domain.Member;
import com.prj.sm.repository.MemberRepository;
import com.prj.sm.service.MemberService;
import com.prj.sm.util.CommonConst;

public class MemberServiceImpl implements MemberService {

	@Autowired
    private MemberRepository memberRepository;

	@Override
	public int joinMember(Member member) {
		int rc = CommonConst.SUCCESS;
		
		if(null != memberRepository.findById(member.getId())) {
			rc = CommonConst.DUPLICATED_MEMBER;
		} else {
			try {
				memberRepository.save(member);
			} catch (IllegalArgumentException ie) {
				rc = CommonConst.INVALID_PARAMETER;
			} catch (Exception e) {
				rc = CommonConst.FAILURE;
			}
		}
		
		return rc;
	}

	@Override
	public int loginMember(String id, String password) throws Exception {
		int rc = CommonConst.SUCCESS;
		Member member = memberRepository.findById(id);
        
		if(null == member) { 
        	rc = CommonConst.CANNOT_FIND_MEMBER;
        }
		
        if(!member.getPassword().equals(password)) {
        	rc = CommonConst.CANNOT_MATCH_PASSWORD;
        }
        
        return rc;
	}

}
