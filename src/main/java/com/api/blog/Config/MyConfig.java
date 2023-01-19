package com.api.blog.Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.api.blog.Security.CustomUserDetailService;
import com.api.blog.Security.JwtAuthenticationEntryPoint;
import com.api.blog.Security.JwtAuthenticationFilter;

/**
 * @author EZPawn
 *
 */
@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class MyConfig extends WebSecurityConfigurerAdapter  {
	@Autowired
	private CustomUserDetailService customUserDetailService;
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().
		disable().
		authorizeHttpRequests().
		antMatchers("/api/v1/auth/login").permitAll().
		antMatchers("/api/users/").permitAll().
	
		anyRequest().
		
		authenticated().
		and().
		exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).
		and().
		sessionManagement().
		sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(this.authenticationFilter,UsernamePasswordAuthenticationFilter.class);
		
	}
//	@Bean
//	 SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{
//		httpSecurity.
//		csrf().
//		disable().
//		authorizeRequests().
//		anyRequest().
//		authenticated().
//		and().
//		httpBasic();
//		httpSecurity.authenticationProvider(authenticationProvider());
//		return httpSecurity.build();
//	}
//	@Bean
//	 DaoAuthenticationProvider authenticationProvider() {
//		
//		DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
//		authenticationProvider.setUserDetailsService(customUserDetailService);
//		authenticationProvider.setPasswordEncoder(passwordEncoder());
//		return authenticationProvider;
//	}
	
	@Bean
	 PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
		
		
	}
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	
	
	
	
	
	
}
