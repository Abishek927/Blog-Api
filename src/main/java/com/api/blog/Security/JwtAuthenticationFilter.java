package com.api.blog.Security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private UserDetailsService detailsService;
	
	@Autowired
	private JwtTokenHelper helper;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//get token
		String jwtToken=request.getHeader("Authorization");
		System.out.println(jwtToken);
	
		
		
		
		String username=null;
		String token=null;
		if(jwtToken!=null && jwtToken.startsWith("Bearer")) {
			token=jwtToken.substring(7);
			
			try {
			username=this.helper.getUsernameFromToken(token);
			}catch(IllegalArgumentException e) {
				System.out.println("Unable to get jwt token");
			}catch(ExpiredJwtException e) {
				System.out.println("Jwt token has expired..");
			}catch(MalformedJwtException e) {
				System.out.println("Invalid jwt");
			}
			
		}else {
			System.out.println("Token is in wrong format");
		}
		
		//once we get the token,we are going to validate it
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails loadUserByUsername = this.detailsService.loadUserByUsername(username);
			if(this.helper.validateToken(token, loadUserByUsername)) {
				UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(loadUserByUsername,null, loadUserByUsername.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
				
			}else {
				System.out.println("Invalid jwt token");
			}
			
			
		}else {
			
			System.out.println("Username is null or context is not null");
			
		}
		
	filterChain.doFilter(request, response);
		
		
		
		
	}

}
