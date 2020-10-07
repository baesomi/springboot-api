package com.prj.sm.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import io.micrometer.core.instrument.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // ������� JWT �� �޾ƿɴϴ�.
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        // ��ȿ�� ��ū���� Ȯ���մϴ�.
        if (StringUtils.isNotEmpty(token) && jwtTokenProvider.isValidToken(token)) {
            // ��ū�� ��ȿ�ϸ� ��ū���κ��� ���� ������ �޾ƿɴϴ�.
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // SecurityContext �� Authentication ��ü�� �����մϴ�.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
