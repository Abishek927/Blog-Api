package com.api.blog.Payloads;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class JwtAuthRequest {
	
	private String username;
	private String password;

}
