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
import com.freetty.entity.Artist;
import com.freetty.repository.ArtistRepo;

public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

	@Autowired
	ArtistRepo artistRepo;
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
			Artist artist = this.artistRepo.findByEmail(email);

			if (artist != null) {
				if (tokenUtil.validateToken(authToken, artist.getEmail(), artist.getPassword())) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							artist.getEmail(), artist.getPassword());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}

		chain.doFilter(request, response);

	}
}
