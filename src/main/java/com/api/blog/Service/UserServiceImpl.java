package com.api.blog.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.blog.Exception.ResourceNotFoundException;
import com.api.blog.Model.User;
import com.api.blog.Payloads.UserDTO;
import com.api.blog.Repositories.UserRepositories;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepositories userRepositories;
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDTO createUser(UserDTO dto) {
			User user=this.modelMapper.map(dto, User.class);
			user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
			
			User resultUser=this.userRepositories.save(user);
			UserDTO resultDTO=this.userToDto(resultUser);
			
		
		return resultDTO;
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Long userId) {
			User user=this.userRepositories.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","id",userId));
		
			user.setUserName(userDTO.getUserName());
			user.setUserEmail(userDTO.getUserEmail());
			user.setPassword(userDTO.getUserPassword());
			user.setAbout(userDTO.getUserAbout());
			this.userRepositories.save(user);
			UserDTO userDTO2=this.userToDto(user);
		
		return userDTO2;
	}

	@Override
	public List<UserDTO> getAllUSer() {
		List<User> users=this.userRepositories.findAll();
			
		List<UserDTO> userDTOs=	users.stream().map(user->this.modelMapper.map(user,UserDTO.class)).collect(Collectors.toList());
				
		
		
		return userDTOs;
	}

	@Override
	public UserDTO getUserById(Long userId) {
		User user=this.userRepositories.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","id",userId));
		UserDTO userDTO=this.modelMapper.map(user,UserDTO.class);
		
		
		
		return userDTO;
	}

	@Override
	public void deleteUser(Long userId) {
		User user=this.userRepositories.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","id",userId));
		this.userRepositories.delete(user);
		
		
	}
	
	public User dtoToUser(UserDTO userDTO) {
		User user=this.modelMapper.map(userDTO,User.class);
	
//		user.setUserName(userDTO.getUserName());
//		user.setUserEmail(userDTO.getUserEmail());
//		user.setPassword(userDTO.getUserPassword());
//		user.setAbout(userDTO.getUserAbout());
		
		
		
		
		return user;
		
	}
	
	public UserDTO userToDto(User user) {
		UserDTO userDTO=this.modelMapper.map(user,UserDTO.class);
	
//		userDTO.setUserName(user.getUserName());
//		userDTO.setUserEmail(user.getUserEmail());
//		userDTO.setUserPassword(user.getPassword());
//		userDTO.setUserAbout(user.getAbout());
		return userDTO;
	}
	

}
