package com.prj.sm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prj.sm.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	Member findById(String id);
	
}