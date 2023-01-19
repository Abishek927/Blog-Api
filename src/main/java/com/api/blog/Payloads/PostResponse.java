package com.api.blog.Payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PostResponse {
	private List<PostDTO> posts;
	private int pageSize;
	private int pageNumber;
	private Long totalElements;
	private int totalPage;
	
	

}
