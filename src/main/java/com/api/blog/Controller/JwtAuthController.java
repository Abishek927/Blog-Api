package com.api.blog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.blog.Payloads.JwtAuthRequest;
import com.api.blog.Payloads.JwtAuthResponse;
import com.api.blog.Security.CustomUserDetailService;
import com.api.blog.Security.JwtTokenHelper;

@RestController
@RequestMapping("/api/v1/auth")
public class JwtAuthController {
	
	
	@Autowired
	private JwtTokenHelper helper;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
			@RequestBody JwtAuthRequest authRequest
			) throws Exception{
		
		authenticate(authRequest.getUsername(),authRequest.getPassword());
		UserDetails userDetails = this.customUserDetailService.loadUserByUsername(authRequest.getUsername());
		System.out.println("the userdetails is:"+userDetails);
		
		String generateToken = this.helper.generateToken(userDetails);
		//System.out.println("the generated token is:"+generateToken);
		
		JwtAuthResponse authResponse=new JwtAuthResponse();
		authResponse.setToken(generateToken);
		return new  ResponseEntity<JwtAuthResponse>(authResponse,HttpStatus.OK);
		
	}

	private void authenticate(String username, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username, password);
		//System.out.println(authenticationToken.getPrincipal());
		
		try {
		this.authenticationManager.authenticate(authenticationToken);
		System.out.println("inside try block");
		
		}catch (BadCredentialsException e) {
			System.out.println("inside catch block");

			throw new Exception("invalid username or password");
		}
		
	}

}
