package com.api.blog.Payloads;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class ImageResponse {
	
	private String imageName;
	
	private Long postId;

	public ImageResponse(String imageName, Long postId) {
		
		this.imageName = imageName;
		this.postId = postId;
		
		
	}
	

}
