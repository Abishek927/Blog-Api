package com.api.blog.Controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.blog.Payloads.ApiResponse;
import com.api.blog.Payloads.UserDTO;
import com.api.blog.Service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	
	
	@PostMapping("/")
	ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
		UserDTO createdUserDTO=this.service.createUser(userDTO);
		return new ResponseEntity<>(createdUserDTO,HttpStatus.CREATED);
	}
	
	@PutMapping("/{userId}")
	ResponseEntity<UserDTO> updateUser(@Valid@RequestBody UserDTO userDTO,@PathVariable("userId")Long uid)
	{
		UserDTO resultUserDTO=this.service.updateUser(userDTO, uid);
		return new ResponseEntity<UserDTO>(resultUserDTO,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{userId}")
	ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId")Long uid){
		this.service.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully!!",true),HttpStatus.OK);
		
		
	}
	
	
	@GetMapping("/{userId}")
	ResponseEntity<UserDTO> getUserById(@PathVariable("userId")Long uid){
		UserDTO userDTO=this.service.getUserById(uid);
		return new ResponseEntity<UserDTO>(userDTO,HttpStatus.OK);
		
		
	}
	
	@GetMapping("/")
	ResponseEntity<List<UserDTO>> getAllUser()
	{
		
		List<UserDTO> dtos=this.service.getAllUSer();
		return new ResponseEntity<List<UserDTO>>(dtos,HttpStatus.OK);
	}
		
	
	

}
