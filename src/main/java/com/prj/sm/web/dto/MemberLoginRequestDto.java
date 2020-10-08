package com.prj.sm.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginRequestDto {
	
	private String id;
	private String password;

	@Builder
	public MemberLoginRequestDto(String id, String password) {
		this.id = id;
		this.password = password;
	}

}
