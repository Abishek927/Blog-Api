package com.api.blog;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BlogAppApiApplication implements CommandLineRunner {
	@Autowired
	private PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApiApplication.class, args);
	}
	
	@Bean
	public ModelMapper getModelMapper() {
		ModelMapper modelMapper=new ModelMapper();
		return modelMapper;
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(encoder.encode("bigyan123"));
		
	}

}
