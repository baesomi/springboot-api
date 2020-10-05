package com.prj.sm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prj.sm.domain.member.Member;
import com.prj.sm.domain.member.MemberRepository;
import com.prj.sm.util.CommonConst;
import com.prj.sm.web.dto.MemberInfoResponseDto;
import com.prj.sm.web.dto.MemberJoinRequestDto;
import com.prj.sm.web.dto.MemberLoginRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberRepository memberRepository;
	
	/**
	 * ȸ������
	 * @param requestDto
	 * @return
	 */
	@Transactional
	public int join(MemberJoinRequestDto requestDto) {
		int rc = CommonConst.SUCCESS;
		memberRepository.save(requestDto.toEntity());
		return rc;
	}
	/**
	 * �α���
	 * @param requestDto
	 * @return
	 */
	@Transactional
	public int login(MemberLoginRequestDto requestDto) {
		// ID
		Member member = memberRepository.findById(requestDto.getId());
		
		// Password
		if(null != member) {
			if(requestDto.getPassword().equals(member.getPassword())) {
				// Update Login date
				member.updateLastLoginDate();
			}
		}
		
		return 0;
	}

	/**
	 * ȸ������ ��ȸ
	 * @param id
	 * @return
	 */
	public MemberInfoResponseDto getInfoById(String id) {
		Member entity = memberRepository.findById(id);
		return new MemberInfoResponseDto(entity);
	}
	
	
    
}