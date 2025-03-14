package com.project3.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project3.blog.config.AppConstants;
import com.project3.blog.payloads.ApiResponse;
import com.project3.blog.payloads.PostDto;
import com.project3.blog.payloads.PostResponse;
import com.project3.blog.services.FileService;
import com.project3.blog.services.PostService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	
//Create post 
	@PostMapping("/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
		PostDto newPost=this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(newPost,HttpStatus.OK);
	}
//Get All post
	@GetMapping
	public ResponseEntity<PostResponse> getAllPosts(	//using hardcode variable
			@RequestParam (value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
			@RequestParam (value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required=false) Integer pageSize,
			@RequestParam (value="sortBy", defaultValue = "postId", required = false) String sortBy
			){
		PostResponse pr= this.postService.getAllPosts(pageNumber,pageSize,sortBy);
		return new ResponseEntity<PostResponse>(pr, HttpStatus.OK);
	}
//Get Post By PostID
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postDto=this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}
//Get by userID
	@GetMapping("/user/{userId}")
	public ResponseEntity<PostResponse> getPostsByUser(
			@PathVariable Integer userId,
			@RequestParam (value="pageNumber",defaultValue="0",required=false) Integer pageNumber,
			@RequestParam (value="pageSize",defaultValue = "5",required=false) Integer pageSize
			){
		PostResponse posts=this.postService.getPostsByUser(userId, pageNumber, pageSize);
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}
//Get by categoryID
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<PostResponse> getPostsByCategory(
			@PathVariable Integer categoryId,
			@RequestParam(value="pageNumber",defaultValue = "0",required = false) Integer pN,
			@RequestParam(value="pageSize", defaultValue = "5", required = false) Integer pS
			){
		PostResponse posts=this.postService.getPostsByCategory(categoryId, pN, pS);
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}
//Update Post
	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPostDto,HttpStatus.OK);
	}
//Delete Post
	@DeleteMapping("/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return new ApiResponse("Post deleted successfully",true);
	}
//Search Post
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword){
		List<PostDto> result=this.postService.searchPost(keyword);
		
		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
	}
	
	
//Post image upload
	@Value("${project.image}")
	private String path;
	@PostMapping("/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId
			) throws IOException {
		PostDto postDto=this.postService.getPostById(postId); 
		
		String fileName =this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
//Serve Image via API
	@GetMapping(value ="/image/{imageName}",produces =MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable ("imageName") String imageName,HttpServletResponse response
			) throws IOException{
		InputStream resource=this.fileService.getResource(path,imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}
}
