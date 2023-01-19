package com.api.blog.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.blog.Model.Post;
import com.api.blog.Payloads.ImageResponse;
import com.api.blog.Payloads.PostDTO;
import com.api.blog.Payloads.PostResponse;
import com.api.blog.Repositories.PostRepositories;
import com.api.blog.Service.PostService;
import com.api.blog.Service.UploadImageService;

@RestController
@RequestMapping("/api")
public class PostController {
	@Autowired
	private PostService postService;
	
	@Autowired
	private PostRepositories postRepositories;
	
	@Autowired
	private UploadImageService imageService;
	
	@Value("${project.image}")
	private String path;
	
	
	
	
	
	@PostMapping("/users/{userId}/categories/{categoryId}/post")
	public ResponseEntity<PostDTO> createPost(@PathVariable("userId")Long userId,@PathVariable("categoryId")Long cId,@RequestBody PostDTO postDTO){
		
		PostDTO postDTO2=this.postService.createPost(userId, cId, postDTO);
		return new ResponseEntity<PostDTO>(postDTO2,HttpStatus.CREATED);
		
	}
	
	
	
	@PutMapping("/users/{userId}/categories/{categoriesId}/post/{postId}")
	public ResponseEntity<PostDTO> updatePost(@PathVariable("userId")Long uId,@PathVariable("categoriesId")Long cId,@PathVariable("postId")Long pId,@RequestBody PostDTO postDTO){
				PostDTO  postDTO2=this.postService.updatePost(uId, cId, pId, postDTO);
				return new ResponseEntity<PostDTO>(postDTO2,HttpStatus.OK);
				
		}
	
	
	@GetMapping("/post")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "5",required = false)Integer pageSize,
			@RequestParam(value="sortBy",defaultValue = "postId",required = false)String sortBy,
			@RequestParam(value = "sortOrder",defaultValue = "ASC",required = false)String sortOrder
			)
	{
		
		PostResponse allPost =this.postService.getAllPost(pageNumber, pageSize, sortBy, sortOrder);
		System.out.println(allPost);
		List<PostDTO> pto=allPost.getPosts();
		for (PostDTO postDTO : pto) {
			System.out.println(postDTO.getPostTitle());
		}
			return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
			
	}
	
	@GetMapping("/users/{userId}/categories/{categoryId}/post/{postId}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable("userId")Long userId,@PathVariable("categoryId")Long categoryId,@PathVariable("postId")Long postId){
		PostDTO postDTO=this.postService.getPostById(userId, categoryId, postId);
		return new ResponseEntity<PostDTO>(postDTO,HttpStatus.OK);
		
		
	}
	
	@GetMapping("/users/{userId}/post")
	public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable("userId")Long uId){
		List<PostDTO> postDTOs=this.postService.getPostsByUser(uId);
		return new ResponseEntity<List<PostDTO>>(postDTOs,HttpStatus.OK);
		
		
	}
	
	@GetMapping("/categories/{categoryId}/post")
	public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable("categoryId")Long categoryId)
	
	{
		
		List<PostDTO> postDTO=this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDTO>>(postDTO,HttpStatus.OK);
		
	}
	
	
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<PostDTO>> searchPostController(@PathVariable("keyword")String keyword){
		List<PostDTO> searchPost = this.postService.searchPost(keyword);
		return new ResponseEntity<List<PostDTO>>(searchPost,HttpStatus.OK);
		
	}
	
	@PostMapping("/upload/image/post/{postId}")
	public ResponseEntity<ImageResponse> uploadImageController(
			@RequestParam("image")MultipartFile file,
			@PathVariable("postId")Long postId
			) throws IOException{
		
		
		Optional<Post> findById = this.postRepositories.findById(postId);
		Post post=findById.get();
		
		String uploadImage = this.imageService.uploadImage(path, file);
		post.setImage(uploadImage);
		this.postRepositories.save(post);
		return new ResponseEntity<ImageResponse>(new ImageResponse(uploadImage,postId),HttpStatus.OK);
		}
	
	
	
	@GetMapping(value="/post/{postId}/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void serveImageController(
			@PathVariable("postId")Long postId,
			@PathVariable("imageName")String imageName,
			HttpServletResponse httpServletResponse
			
			
			
			
			
			) throws IOException {
		
		
		Optional<Post> findById = this.postRepositories.findById(postId);
		Post post=findById.get();
		
		httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
		InputStream serveImage = this.imageService.serveImage(path,imageName);
		StreamUtils.copy(serveImage,httpServletResponse.getOutputStream());
		
		
		
	}
	
}
	

