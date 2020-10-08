package com.prj.sm.web.dto;


import java.util.Date;
import com.prj.sm.domain.member.Member;
import lombok.Getter;

@Getter
public class MemberInfoResponseDto {

	private String id;
	private String name;
	private Date lastLoginDate;
	
	public MemberInfoResponseDto(Member entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.lastLoginDate = entity.getLastLoginDate();
	}
}
