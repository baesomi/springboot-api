package com.prj.sm.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.prj.sm.config.security.JwtTokenProvider;
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
		if(null != requestDto) {
			// check ID
			String id = requestDto.getId();
			if (StringUtils.isNotEmpty(id) && CommonUtil.isValidEmail(id)) {
				if(null != memberService.getInfoById(id)) {
					map.put("errorCode", CommonConst.DUPLICATED_MEMBER);
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
				// check Password
				String password = requestDto.getPassword();
				if(StringUtils.isNotEmpty(password) && CommonUtil.isValidPw(password)) {
					// join
					rc = memberService.join(requestDto);
				} else {
					map.put("errorCode", CommonConst.INVALID_PARAMETER);
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			}
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
			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			rc = CommonConst.FAILURE;
			map.put("errorMsg", e.getMessage());
		}
		map.put("success", CommonUtil.isSuccess(rc));
		map.put("accessToken", accessToken);
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
