package com.prj.sm.web.dto;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import com.prj.sm.domain.member.Member;

import lombok.Getter;

@Getter
public class MemberInfoResponseDto {

	//������̸�, Email, ���� �α��� �Ͻ�
	private String id;
	private String name;
	private Date lastLoginDate;
	
	
	public MemberInfoResponseDto(Member entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.lastLoginDate = entity.getLastLoginDate();
	}
}
