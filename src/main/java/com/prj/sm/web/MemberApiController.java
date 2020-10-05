package com.prj.sm.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.prj.sm.service.MemberService;
import com.prj.sm.util.*;
import com.prj.sm.web.dto.MemberInfoResponseDto;
import com.prj.sm.web.dto.MemberJoinRequestDto;
import com.prj.sm.web.dto.MemberLoginRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MemberApiController {
	
	private final MemberService memberService;
	
	@ResponseBody
	@PostMapping(value="/v1/member/join")
	public int join(@RequestBody MemberJoinRequestDto requestDto) {
		int rc = CommonConst.SUCCESS;
		/**
		 1. ID는 반드시 email 형식이어야 합니다. 
		 2. 비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상 의 문자열로 생성해야 합니다. 
		 3. 비밀번호는 서버에 저장될 때에는 반드시 단방향 해시 처리가 되어야 합니다
		*/
		
		return memberService.join(requestDto);
	}
	
	@ResponseBody
	@PostMapping(value="/v1/member/login")
	public int login(@RequestBody MemberLoginRequestDto requestDto) {
		int rc = CommonConst.SUCCESS;
		/**
		 1. ID는 반드시 email 형식이어야 합니다. 
		 2. 비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상 의 문자열로 생성해야 합니다. 
		 3. 비밀번호는 서버에 저장될 때에는 반드시 단방향 해시 처리가 되어야 합니다
		*/
		
		return memberService.login(requestDto);
	}
	
	@GetMapping(value="/v1/member/info/{id}")
	public MemberInfoResponseDto getMemberInfoById(@PathVariable String id) {
		return memberService.getInfoById(id);
	}
}
