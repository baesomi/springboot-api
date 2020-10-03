package com.prj.sm.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Members")
public class Member {
/**
 * 사용자 정보
 * */
    @Id
    @GeneratedValue
    private int seq;
    
    // email 
    @Column(nullable = false, unique = true)
    private String id;

    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String name;
    
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateDate;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}