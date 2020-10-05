package com.prj.sm.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

@Getter
@NoArgsConstructor
@Entity
public class Member {
/**
 * 사용자 정보
 * */
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    
    @Column(length = 100, nullable = false, unique = true)
    private String id;
    
    @Column(length = 100, nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String password;
    
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginDate;

    @Builder
    public Member(String id, String name, String password) {
    	this.id = id;
    	this.name = name;
    	this.password = password;
    }
    
    /**
     * login 일시 update
     */
    public void updateLastLoginDate() {
    	lastLoginDate = new Date(System.currentTimeMillis());
    }
}