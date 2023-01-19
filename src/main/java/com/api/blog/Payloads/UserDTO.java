package com.api.blog.Payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
	
	@NotEmpty(message = "UserName should not be null!!!")
	@Size(min = 5,max = 8,message = "Username should be 5 to 8 character long")
	private String userName;
	@Email(regexp ="^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$" ,message = "Please!Provide valid email!!")
	
	private String userEmail;
	
	@NotEmpty(message="Password should not be null!!!")
	@Size(min = 8,max=16,message = "Password should be 8 to 16 character long!!")
	private String userPassword;
	
	@NotEmpty(message = "User must provide their information")
	private String userAbout;
	

}
