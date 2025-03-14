package com.project3.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project3.blog.payloads.PostDto;
import com.project3.blog.payloads.PostResponse;

@Service

public interface PostService {
	//create
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	//update
	PostDto updatePost(PostDto postDto,Integer postId);
	//delete
	void deletePost(Integer postId);
	
	//getAll
	PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy);
	//getOne
	PostDto getPostById(Integer postId);
	//get PostDto by category
//	List<PostDto> getPostsByCategory(Integer categoryId);
	PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);
	//get PostDto by user
	PostResponse getPostsByUser(Integer userId,Integer pageNumber,Integer pageSize);
	//get PostDto by search
	List<PostDto> searchPost(String keyword);
		
}
