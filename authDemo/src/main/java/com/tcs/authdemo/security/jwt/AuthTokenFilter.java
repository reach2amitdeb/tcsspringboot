package com.tcs.authdemo.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tcs.authdemo.security.services.UserDetailsServiceImpl;

public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	JwtUtils jwtUtils;
	@Autowired
private UserDetailsServiceImpl userDetailsServiceImpl;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// collect the token
		String jwt = parseJwt(request);
		
		// get username from token
		String username = jwtUtils.getUserNameFromJwtToken(jwt);
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		// authenticate the token
		filterChain.doFilter(request, response);

	}
	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		
		if(StringUtils.hasText(headerAuth)&& headerAuth.startsWith("Bearer")) {
			return headerAuth.substring(7,headerAuth.length());
		}
		return null;
	}

}
