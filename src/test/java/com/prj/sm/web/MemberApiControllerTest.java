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
import com.prj.sm.util.EncryptHelper;
import com.prj.sm.web.dto.MemberJoinRequestDto;
import com.prj.sm.web.dto.MemberLoginRequestDto;

import java.util.List;
import java.util.Map;

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

	@After
	public void cleanup() throws Exception{
		memberRepository.deleteAll();
	}

	@Test
	public void testJoin() throws Exception {
		//given
		String id = "test@gmail.com";
		String name = "John Doe";
		String password = "abCde12345678";
		
		MemberJoinRequestDto requestDto = MemberJoinRequestDto.builder()
														.id(id)
														.password(password)
														.name(name)
														.build();

		final String URL_JOIN = "http://localhost:" + port + "/v1/member/join";

		//when
		ResponseEntity<Object> responseEntity = restTemplate.postForEntity(URL_JOIN, requestDto, Object.class);

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
		String password = "abCde12345678";
		
		MemberLoginRequestDto requestDto = MemberLoginRequestDto
													.builder()
													.id(id)
													.password(password)
													.build();
		final String URL_LOGIN = "http://localhost:" + port + "/v1/member/login";

		//when
		ResponseEntity<Map> responseEntity = restTemplate.postForEntity(URL_LOGIN, requestDto, Map.class);

		//then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		System.out.println("=================================================================");
		System.out.println("responseEntity.getBody()=" + responseEntity.getBody() );
		System.out.println("response=" + responseEntity.getBody().toString());
		System.out.println("=================================================================");
	}

	@Deprecated
	public void testGetAuthorizedMemberInfo() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		String accessToken= ""; //need input
		headers.set("X-AUTH-TOKEN", accessToken);

		final String URL_GETINFO = "http://localhost:" + port + "/v1/member/info";
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(null, headers);
		ResponseEntity<Object> responseEntity = restTemplate.exchange(URL_GETINFO, HttpMethod.GET ,request, Object.class);

		//then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

		System.out.println("=================================================================");
		System.out.println("responseEntity.getStatusCode() " + responseEntity.getStatusCode() );
		System.out.println("responseEntity.getBody() " + responseEntity.getBody() );
		System.out.println("=================================================================");

	}
}
