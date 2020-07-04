package com.freetty.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.freetty.common.TokenUtil;
import com.freetty.entity.Customer;
import com.freetty.repository.CustomerRepo;

public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

	@Autowired
	CustomerRepo customerRepo;
	@Autowired
	TokenUtil tokenUtil;
	@Value("${freetty.token.header}")
	private String tokenHeader;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String authToken = httpRequest.getHeader(this.tokenHeader);
		String email = tokenUtil.getUserIdFromToken(authToken);
		System.out.println("<필터진입> 아이디 : " + email);
		System.out.println("<필터진입> 인증토큰 : " + authToken);

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			Customer customer = this.customerRepo.findByEmail(email);

			if (customer != null) {
				if (tokenUtil.validateToken(authToken, customer.getEmail(), customer.getPassword())) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							customer.getEmail(), customer.getPassword());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
					SecurityContextHolder.getContext().setAuthentication(authentication);
					System.out.println("인증완료");
				}
			}
		}

		chain.doFilter(request, response);

	}
}
