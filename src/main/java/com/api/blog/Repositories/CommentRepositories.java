package com.api.blog.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.blog.Model.Comment;
import com.api.blog.Model.Post;

@Repository
public interface CommentRepositories extends JpaRepository<Comment,Long> {
	
	public List<Comment> findByPost(Post post);

}
