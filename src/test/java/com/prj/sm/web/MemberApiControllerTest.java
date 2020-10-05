package com.prj.sm.web;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.prj.sm.domain.member.Member;
import com.prj.sm.domain.member.MemberRepository;
import com.prj.sm.util.CommonConst;
import com.prj.sm.web.dto.MemberJoinRequestDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberApiControllerTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@After
	public void cleanup() throws Exception{
		memberRepository.deleteAll();
	}
	
	@Test
	public void testJoin() throws Exception {
		//given
		String id = "test@gmail.com";
		String name = "John Doe";
		String password = "12345";
		
		MemberJoinRequestDto requestDto = MemberJoinRequestDto
													.builder()
													.id(id)
													.password(password)
													.name(name)
													.build();
		String url = "http://localhost:"+port+"/v1/member/join";
		
		//when
		ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, requestDto, Object.class);
		
		//then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isEqualTo(CommonConst.SUCCESS);
		
		List<Member> all = memberRepository.findAll();
		assertThat(all.get(0).getId()).isEqualTo(id);
		assertThat(all.get(0).getPassword()).isEqualTo(password);
		assertThat(all.get(0).getName()).isEqualTo(name);

	}
}
