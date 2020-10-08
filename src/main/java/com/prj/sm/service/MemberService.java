package com.prj.sm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prj.sm.config.security.JwtTokenProvider;
import com.prj.sm.domain.member.Member;
import com.prj.sm.domain.member.MemberRepository;
import com.prj.sm.util.CommonConst;
import com.prj.sm.util.EncryptHelper;
import com.prj.sm.web.dto.MemberInfoResponseDto;
import com.prj.sm.web.dto.MemberJoinRequestDto;
import com.prj.sm.web.dto.MemberLoginRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	private final EncryptHelper encryptHelper;
	private final JwtTokenProvider jwtTokenProvider;
	private final MemberRepository memberRepository;
	/**
	 * 회원가입
	 * @param requestDto
	 * @return
	 */
	@Transactional
	public int join(MemberJoinRequestDto requestDto) {
		int rc = CommonConst.SUCCESS;
		Member joinMember = new Member().builder()
									.id(requestDto.getId())
									.password(encryptHelper.encrypt(requestDto.getPassword()))
									.name(requestDto.getName())
									.build();
		
		memberRepository.save(joinMember);
		return rc;
	}
	/**
	 * 로그인
	 * @param requestDto
	 * @return
	 */
	@Transactional
	public String login(MemberLoginRequestDto requestDto) {
		String accessToken = "";
		Member member = memberRepository.findById(requestDto.getId());
		if(null != member) {
			if(encryptHelper.isMatch(requestDto.getPassword(), member.getPassword())) {
				// Update Login date
				member.updateLastLoginDate();
				// Create Token
				accessToken = jwtTokenProvider.createToken(member.getUsername(), member.getName());
			} else {
				throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
			}
		} else {
			throw new IllegalArgumentException("가입되지 않은 E-MAIL입니다.");
		}
		return accessToken;
	}

	/**
	 * 회원정보 조회
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public MemberInfoResponseDto getInfoById(String id) {
		Member entity = memberRepository.findById(id);
		return new MemberInfoResponseDto(entity);
	}
}