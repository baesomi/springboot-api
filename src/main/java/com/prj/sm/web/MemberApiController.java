package com.prj.sm.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.prj.sm.config.security.JwtTokenProvider;
import com.prj.sm.domain.member.Member;
import com.prj.sm.service.MemberService;
import com.prj.sm.util.*;
import com.prj.sm.web.dto.MemberInfoResponseDto;
import com.prj.sm.web.dto.MemberJoinRequestDto;
import com.prj.sm.web.dto.MemberLoginRequestDto;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MemberApiController {
	
	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;
	
	@ResponseBody
	@PostMapping(value="/v1/member/join")
	public ResponseEntity<Map> join(@RequestBody MemberJoinRequestDto requestDto) {
		int rc = CommonConst.SUCCESS;
		Map map = new HashMap();
		/**
		 1. ID�� �ݵ�� email �����̾�� �մϴ�. 
		 2. ��й�ȣ�� ���� �빮��, ���� �ҹ���, ����, Ư������ �� 3���� �̻����� 12�ڸ� �̻� �� ���ڿ��� �����ؾ� �մϴ�. 
		 3. ��й�ȣ�� ������ ����� ������ �ݵ�� �ܹ��� �ؽ� ó���� �Ǿ�� �մϴ�
		*/
		if(null != requestDto) {
			
			String id = requestDto.getId();
			String password = requestDto.getPassword();
			String name = requestDto.getName();

			if (StringUtils.isNotEmpty(id) && CommonUtil.isValidEmail(id)) {

			}
			
			rc = memberService.join(requestDto);
		}
		map.put("success", CommonUtil.isSuccess(rc));
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value="/v1/member/login")
	public ResponseEntity<Map> login(@RequestBody MemberLoginRequestDto requestDto, 
				HttpServletResponse response){
		int rc = CommonConst.SUCCESS;
		Map map = new HashMap();
		String accessToken = "";
		try {
			accessToken =  memberService.login(requestDto);
        } catch (IllegalArgumentException e) {
            rc = CommonConst.FAILURE;
            map.put("error_msg", e.getMessage());
        } catch (Exception e) {
            rc = CommonConst.FAILURE;
            map.put("error_msg", e.getMessage());
        }
		map.put("success", CommonUtil.isSuccess(rc));
		map.put("access_token", accessToken);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@GetMapping(value="/v1/member/info")
	public ResponseEntity<MemberInfoResponseDto> getAuthorizedMemberInfo(HttpServletRequest request) {
		String token = jwtTokenProvider.resolveToken(request);
		if(!jwtTokenProvider.isValidToken(token)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} 
		MemberInfoResponseDto memberInfo = memberService.getInfoById(jwtTokenProvider.getUserPk(token));
		if(null == memberInfo) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(memberInfo, HttpStatus.OK); 
	}
}
