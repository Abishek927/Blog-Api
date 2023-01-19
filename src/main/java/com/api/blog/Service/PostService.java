package com.api.blog.Service;

import java.util.List;

import com.api.blog.Payloads.PostDTO;
import com.api.blog.Payloads.PostResponse;

public interface PostService {
	
	public PostDTO createPost(Long userId,Long categoryId,PostDTO postDTO);
	public PostDTO updatePost(Long userId,Long categoryId,Long postId,PostDTO postDTO);
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);
	public PostDTO getPostById(Long userId,Long categoryId,Long postId);
	
	public List<PostDTO> getPostsByUser(Long userId);
	public List<PostDTO> getPostsByCategory(Long categoryId);
	
	public List<PostDTO> searchPost(String keyword);
	
	

}
