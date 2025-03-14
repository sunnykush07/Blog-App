package com.project3.blog.services.impl;

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

import com.project3.blog.entities.Category;
import com.project3.blog.entities.Post;
import com.project3.blog.entities.User;
import com.project3.blog.exceptions.ResourceNotFoundException;
import com.project3.blog.payloads.PostDto;
import com.project3.blog.payloads.PostResponse;
import com.project3.blog.repositories.CategoryRepo;
import com.project3.blog.repositories.PostRepo;
import com.project3.blog.repositories.UserRepo;
import com.project3.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private UserRepo userRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User Id", userId));
		Category caty=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		// setting post data of respectively user and category 
		Post post=this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setPostDate(new Date());
		post.setUser(user);
		post.setCategory(caty);
		
		Post newPost=this.postRepo.save(post);
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		//category,image,
		Post save=this.postRepo.save(post);
		return this.modelMapper.map(save, PostDto.class);
	}

	@Override
	public void deletePost(Integer PostId) {
		Post post =this.postRepo.findById(PostId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", PostId));
		this.postRepo.delete(post);

	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy) {
		// using pagination and sorting to return page.
		// Sort sort=Sort.by(sortBy).descending()   pass in function 
		Pageable p=PageRequest.of(pageNumber, pageSize,Sort.by(sortBy));
		Page<Post>pagePost=this.postRepo.findAll(p);
		
		List<Post> allPost=pagePost.getContent();
		List<PostDto>postDtos=allPost.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse pr=new PostResponse();
		pr.setContents(postDtos);
		pr.setPageNumber(pagePost.getNumber());
		pr.setPageSize(pagePost.getSize());
		pr.setTotalElements(pagePost.getTotalElements());
		pr.setTotalPages(pagePost.getTotalPages());
		pr.setLastPage(pagePost.isLast());
		return pr;
		
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post Id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {
		Category caty= this. categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		Pageable p=PageRequest.of(pageNumber, pageSize);
		Page<Post> posts = this.postRepo.findByCategory(caty, p);
		List<PostDto>postDtos = posts.getContent().stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse pr=new PostResponse();
		pr.setContents(postDtos);
		pr.setTotalElements(posts.getTotalElements());
		pr.setLastPage(posts.isLast());
		pr.setPageNumber(posts.getNumber());
		pr.setPageSize(posts.getSize());
		pr.setTotalPages(posts.getTotalPages());
		
		return pr;
	}

	@Override
	public PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize) {
	    // Create Pageable object for pagination
	    Pageable pageable = PageRequest.of(pageNumber, pageSize);

	    // Fetch user or throw exception if not found
	    User user = this.userRepo.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

	    // Fetch paginated posts using pageable
	    Page<Post> postsPage = this.postRepo.findByUser(user, pageable);

	    // Convert Page<Post> to List<PostDto>
	    List<PostDto> postDtos = postsPage.getContent()
	            .stream()
	            .map(post -> this.modelMapper.map(post, PostDto.class))
	            .collect(Collectors.toList());

	    // Create and return PostResponse object
	    PostResponse postResponse = new PostResponse();
	    postResponse.setContents(postDtos);
	    postResponse.setPageNumber(postsPage.getNumber());
	    postResponse.setPageSize(postsPage.getSize());
	    postResponse.setTotalElements(postsPage.getTotalElements());
	    postResponse.setTotalPages(postsPage.getTotalPages());
	    postResponse.setLastPage(postsPage.isLast());

	    return postResponse;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> posts=this.postRepo.findByTitleContaining(keyword);
		return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
	}

}
