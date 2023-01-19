package com.api.blog.Service;


import java.util.List;

import com.api.blog.Payloads.UserDTO;

public interface UserService {
	
	
	public UserDTO createUser(UserDTO dto);
	
	public UserDTO updateUser(UserDTO userDTO,Long userId);
	
	public List<UserDTO> getAllUSer();
	public UserDTO getUserById(Long userId);
	
	public void deleteUser(Long userId);

}
