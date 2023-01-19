package com.api.blog.Payloads;

import java.util.Date;

import com.api.blog.Model.Category;
import com.api.blog.Model.Comment;
import com.api.blog.Model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class PostDTO {
	private Long postId;
	private String postTitle;

	private String postContent;
	private Date addedDateTime;

	
	private String image;
	
	private User user;
	private Category category;
	private Comment comment;
}
