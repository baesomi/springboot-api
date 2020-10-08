package com.prj.sm.domain.member;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {
	
	@Autowired
	MemberRepository memberRepository;
	
	@After
	public void cleanup() {
		memberRepository.deleteAll();
	}
	
	@Test
	public void testInsertSelect() {
		//given
		String id = "test@gmail.com";
		String name = "John Doe";
		String password = "12345";
		
		memberRepository.save(Member.builder()
									.id(id)
									.name(name)
									.password(password)
									.build());
		//when
		List<Member> memberList = memberRepository.findAll();
		
		//then
		Member member = memberList.get(0);
		assertThat(member.getId()).isEqualTo(id);
		assertThat(member.getName()).isEqualTo(name);
		assertThat(member.getPassword()).isEqualTo(password);
    }
}
