package com.prj.sm.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.micrometer.core.instrument.util.StringUtils;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
	
    private static String SECRET_KEY = "bearer";
    
    private static final long TOKEN_VALID_TIME= 30 * 60 * 1000L;
	private static final String TOKEN_HEADER_STR= "X-AUTH-TOKEN";
    
    private final UserDetailsService userDetailsService;

    // Encoding SecretKey
    @PostConstruct
    protected void init() {
    	SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    // JWT ��ū ����
    public String createToken(String userPk, String userName) {
    	String token = "";

    	if(StringUtils.isNotEmpty(userPk) && StringUtils.isNotEmpty(userName)) {
	        Claims claims = Jwts.claims().setSubject(userPk); 
	        claims.put("userId", userPk);
	        claims.put("userName", userName); 
	        Date now = new Date();
	        token = Jwts.builder()
		                .setClaims(claims) // ���� ����
		                .setIssuedAt(now) // ��ū ���� �ð� ����
		                .setExpiration(new Date(now.getTime() + TOKEN_VALID_TIME)) // set Expire Time
		                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
		                .compact();
    	}
    	return token;
    }

    // JWT ��ū���� ���� ���� ��ȸ
    public Authentication getAuthentication(String token) {
    	if(StringUtil.isNullOrEmpty(token)) {
    		throw new NullPointerException();
    	}
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // ��ū���� ȸ�� ���� ����
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    // Get from Request Header { "X-AUTH-TOKEN" : "TOKEN VALUE' }
    public String resolveToken(HttpServletRequest request) {
    	String token = "";
    	if(null != request) {
    		token = request.getHeader(TOKEN_HEADER_STR);
    	}
        return token;
    }

    // ��ū�� ��ȿ�� + �������� Ȯ��
    public boolean isValidToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (IllegalArgumentException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}