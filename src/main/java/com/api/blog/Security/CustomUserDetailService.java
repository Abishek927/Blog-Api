package com.api.blog.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.blog.Exception.ResourceNotFoundException;
import com.api.blog.Model.User;
import com.api.blog.Repositories.UserRepositories;

@Service
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	private UserRepositories repositories;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = this.repositories.findByUserEmail(username).orElseThrow(()->new ResourceNotFoundException("user", "email"+username,(long) 0));
		//System.out.println(user);
		
		return user;
	}

}
