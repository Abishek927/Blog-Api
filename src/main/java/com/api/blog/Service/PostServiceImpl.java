package com.api.blog.Service;

import java.util.Date;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.api.blog.Exception.ResourceNotFoundException;
import com.api.blog.Model.Category;
import com.api.blog.Model.Post;
import com.api.blog.Model.User;
import com.api.blog.Payloads.PostDTO;
import com.api.blog.Payloads.PostResponse;
import com.api.blog.Repositories.CategoryRepositories;
import com.api.blog.Repositories.PostRepositories;
import com.api.blog.Repositories.UserRepositories;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private UserRepositories repositories;

	@Autowired
	private CategoryRepositories categoryRepositories;

	@Autowired
	private PostRepositories postRepositories;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDTO createPost(Long userId, Long categoryId, PostDTO postDTO) {
		PostDTO resultPostDTO = new PostDTO();
		User user = this.repositories.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));

		Category category = this.categoryRepositories.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		if (user != null && category != null) {
			Post post = this.modelMapper.map(postDTO, Post.class);
			post.setCategory(category);
			post.setAddedDateTime(new Date());
			post.setImage("default.png");
			post.setUser(user);
			Post resultPost = this.postRepositories.save(post);
			resultPostDTO = this.modelMapper.map(resultPost, PostDTO.class);

		}

		return resultPostDTO;
	}

	@Override
	public PostDTO updatePost(Long userId, Long categoryId, Long postId, PostDTO postDTO) {
		
		PostDTO updatedPostDTO=new PostDTO();
		User user = this.repositories.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));

		Category category = this.categoryRepositories.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		Post post=this.postRepositories.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","postId", postId));
		
		if(post.getCategory().equals(category)&&post.getUser().equals(user)) {
			post.setPostTitle(postDTO.getPostTitle());
			post.setPostContent(postDTO.getPostContent());
			post.setImage(postDTO.getImage());
			Post resultPost=this.postRepositories.save(post);
			updatedPostDTO=this.modelMapper.map(resultPost,PostDTO.class);
			
			
			
		}

		return updatedPostDTO;
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder) {
		
		Sort sort=null;
		if(sortOrder.equalsIgnoreCase("asc")) {
			sort  = Sort.by(sortBy).ascending();
		}
		else {
		 sort = Sort.by(sortBy).descending();
		}
		
		
		Pageable page=PageRequest.of(pageNumber,pageSize,sort);
		Page<Post> posts=this.postRepositories.findAll(page);
		List<Post> posts2=posts.getContent();
		
		List<PostDTO> postDTOs=posts.stream().map((post)->this.modelMapper.map(posts2, PostDTO.class)).collect(Collectors.toList());

		
		
		PostResponse postResponse=new PostResponse();
		postResponse.setPageNumber(posts.getNumber());
		postResponse.setPosts(postDTOs);
		//System.out.print(postResponse.getPosts());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPage(posts.getTotalPages());
		
		
		
		
		return postResponse;
	}

	@Override
	public PostDTO getPostById(Long userId, Long categoryId, Long postId) {
		PostDTO resultPostDTO=new  PostDTO();
		
		User user = this.repositories.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));

		Category category = this.categoryRepositories.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		Post post=this.postRepositories.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","postId", postId));

		
		if(post.getCategory().equals(category)&&post.getUser().equals(user)) {
			resultPostDTO=this.modelMapper.map(post,PostDTO.class);
			

			
			
		}
		
		
		
		
		return resultPostDTO;
	}

	
	
	@Override
	public List<PostDTO> getPostsByUser(Long userId) {
		User user = this.repositories.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
		
		List<Post> posts=this.postRepositories.findByUser(user);
		
		List<PostDTO> dtos=posts.stream().map((post)->this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

		
		
		return dtos;
	}

	@Override
	public List<PostDTO> getPostsByCategory(Long categoryId) {
		
		Category category = this.categoryRepositories.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		
		List<Post> posts=this.postRepositories.findByCategory(category);
		
		List<PostDTO> postDTOs=posts.stream().map((post)->this.modelMapper.map(post,PostDTO.class)).collect(Collectors.toList());
		
		return postDTOs;
	}

	@Override
	public List<PostDTO> searchPost(String keyword) {
		
		List<Post> findByPostTitleContain = this.postRepositories.findByPostTitleContaining(keyword);
		List<PostDTO> collect = findByPostTitleContain.stream().map((post)->this.modelMapper.map(findByPostTitleContain, PostDTO.class)).collect(Collectors.toList());
		
		return collect;
	}

	

}
