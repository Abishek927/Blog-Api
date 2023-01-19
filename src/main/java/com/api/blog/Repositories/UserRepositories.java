package com.api.blog.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.blog.Model.User;
@Repository
public interface UserRepositories extends JpaRepository<User,Long> {
	
	public Optional<User> findByUserEmail(String email);
	

              
}
