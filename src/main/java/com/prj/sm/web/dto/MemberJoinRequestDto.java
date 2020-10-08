package com.prj.sm.web.dto;

import com.prj.sm.domain.member.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberJoinRequestDto {
	private String id;
	private String name;
	private String password;

	@Builder
	public MemberJoinRequestDto(String id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public Member toEntity() {
		return Member.builder()
					.id(id)
					.name(name)
					.password(password)
					.build();
	}
	
}
