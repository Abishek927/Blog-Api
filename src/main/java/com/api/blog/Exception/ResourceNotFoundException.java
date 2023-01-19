package com.api.blog.Exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ResourceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String resourceName;
	private String resourceField;
	private Long fieldValue;
	public ResourceNotFoundException(String resourceName, String resourceField, Long fieldValue) {
		super(String.format("%s resource not found with %s:%S",resourceName,resourceField,fieldValue));
		this.resourceName = resourceName;
		this.resourceField = resourceField;
		this.fieldValue = fieldValue;
	}

	
	
	
	

}
