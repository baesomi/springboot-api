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
		 1. ID�� �ݵ�� email �����̾�� �մϴ�. 
		 2. ��й�ȣ�� ���� �빮��, ���� �ҹ���, ����, Ư������ �� 3���� �̻����� 12�ڸ� �̻� �� ���ڿ��� �����ؾ� �մϴ�. 
		 3. ��й�ȣ�� ������ ����� ������ �ݵ�� �ܹ��� �ؽ� ó���� �Ǿ�� �մϴ�
		*/
		
		return memberService.join(requestDto);
	}
	
	@ResponseBody
	@PostMapping(value="/v1/member/login")
	public int login(@RequestBody MemberLoginRequestDto requestDto) {
		int rc = CommonConst.SUCCESS;
		/**
		 1. ID�� �ݵ�� email �����̾�� �մϴ�. 
		 2. ��й�ȣ�� ���� �빮��, ���� �ҹ���, ����, Ư������ �� 3���� �̻����� 12�ڸ� �̻� �� ���ڿ��� �����ؾ� �մϴ�. 
		 3. ��й�ȣ�� ������ ����� ������ �ݵ�� �ܹ��� �ؽ� ó���� �Ǿ�� �մϴ�
		*/
		
		return memberService.login(requestDto);
	}
	
	@GetMapping(value="/v1/member/info/{id}")
	public MemberInfoResponseDto getMemberInfoById(@PathVariable String id) {
		return memberService.getInfoById(id);
	}
}
