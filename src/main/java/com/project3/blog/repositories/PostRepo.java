package com.project3.blog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project3.blog.entities.Category;
import com.project3.blog.entities.Post;
import com.project3.blog.entities.User;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
	Page<Post> findByUser(User user, Pageable p);
	Page<Post> findByCategory(Category caty, Pageable p);
//	List<Post> findByCategory(Category caty);
//	List<Post> findByCategoryId(Integer catyId);
	List<Post> findByTitleContaining(String title);
}
