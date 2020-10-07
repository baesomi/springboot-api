package com.prj.sm.web;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

import com.prj.sm.domain.member.Member;
import com.prj.sm.domain.member.MemberRepository;
import com.prj.sm.util.CommonConst;
import com.prj.sm.util.EncryptHelper;
import com.prj.sm.web.dto.MemberJoinRequestDto;
import com.prj.sm.web.dto.MemberLoginRequestDto;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberApiControllerTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private EncryptHelper encryptHelper;
	
	String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsInVzZXJJZCI6InRlc3RAZ21haWwuY29tIiwidXNlck5hbWUiOiJKb2huIERvZSIsImlhdCI6MTYwMjA5MjM4NSwiZXhwIjoxNjAyMDk0MTg1fQ.huflCfoM_P6O3yE8O0MwKR2olHx1XXRiyTahGiZ1cWc";
	
	@Deprecated
	public void cleanup() throws Exception{
		memberRepository.deleteAll();
	}
	
	@Deprecated
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
		
		List<Member> all = memberRepository.findAll();
		assertThat(all.get(0).getId()).isEqualTo(id);
		assertTrue(encryptHelper.isMatch(password,all.get(0).getPassword()));
		assertThat(all.get(0).getName()).isEqualTo(name);

	}
	
	@Deprecated
	public void testLogin() throws Exception {
		//given
		String id = "test@gmail.com";
		String name = "John Doe";
		String password = "12345";
		
		MemberLoginRequestDto requestDto = MemberLoginRequestDto
													.builder()
													.id(id)
													.password(password)
													.build();
		String url = "http://localhost:"+port+"/v1/member/login";
		
		//when
		ResponseEntity<Map> responseEntity = restTemplate.postForEntity(url, requestDto, Map.class);
		
		//then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		Map obj = responseEntity.getBody();
		accessToken = (String) obj.get("access_token");
		System.out.println("=================================================================");
		System.out.println("responseEntity.getBody() " + responseEntity.getBody() );
		System.out.println("obj " + obj.toString());
		System.out.println("=================================================================");
	}
	
	@Test
	public void testGetAuthorizedMemberInfo() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-AUTH-TOKEN", accessToken);
		
		String url = "http://localhost:"+port+"/v1/member/info";
		
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(null, headers);
		
		ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.GET ,request, Object.class);
		
		//then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		System.out.println("=================================================================");
		System.out.println("responseEntity.getStatusCode() " + responseEntity.getStatusCode() );
		System.out.println("responseEntity.getBody() " + responseEntity.getBody() );
		System.out.println("=================================================================");
		
	}
}
