package com.api.blog.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.blog.Model.Category;
import com.api.blog.Model.Post;
import com.api.blog.Model.User;

@Repository
public interface PostRepositories extends JpaRepository<Post,Long> {
	
	public List<Post> findByUser(User user);
	
	public List<Post> findByCategory(Category category);
	
	public List<Post> findByPostTitleContaining(String keyword);

}
