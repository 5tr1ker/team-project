package com.team.todaycheck.main.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JwtAuthenticationFilter extends GenericFilterBean {

	private JwtTokenProvider jwtTokenProvider;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String token = jwtTokenProvider.resolveToken((HttpServletRequest) request); // ������� JWT ����
		if (token != null && jwtTokenProvider.validateToken(token)) { // ��ȿ�� ��ū?
			Authentication authentication = jwtTokenProvider.getAuthentication(token); // ��ȿ�� ��ū�� ������ ������
			SecurityContextHolder.getContext().setAuthentication(authentication); // SecurityContext �� Authentication
																					// ��ü�� ����
		}
		chain.doFilter(request, response);
	}

	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}
}
