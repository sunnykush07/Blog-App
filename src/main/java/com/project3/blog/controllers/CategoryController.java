package com.project3.blog.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project3.blog.payloads.ApiResponse;
import com.project3.blog.payloads.CategoryDto;
import com.project3.blog.services.CategoryService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	//Post
	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto createDto=this.categoryService.createCaty(categoryDto);
		return new ResponseEntity<CategoryDto>(createDto,HttpStatus.CREATED);
	}
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		List<CategoryDto> categoryDtos=this.categoryService.getAllCaty();
		return ResponseEntity.ok(categoryDtos);
	}
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId){
		CategoryDto categoryDto=this.categoryService.getCatyById(categoryId);
		return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
	}
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
		CategoryDto updateDto=this.categoryService.updateCaty(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(updateDto,HttpStatus.OK);
	}
	//Delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
		this.categoryService.deleteCaty(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted successfully!!",true),HttpStatus.OK);
	}
	
	

}
