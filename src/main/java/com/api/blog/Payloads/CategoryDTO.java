package com.api.blog.Payloads;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {
	
	private Long catId;
	
	@NotEmpty(message="Title should not be empty!!")
	private String catTitle;
	@NotEmpty(message = "Description should not be empty!!")
	private String catDescription;
	

}
