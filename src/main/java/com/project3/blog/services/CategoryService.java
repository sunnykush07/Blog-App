package com.project3.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project3.blog.payloads.CategoryDto;

@Service
public interface CategoryService { //interface method-> public by default
	//create
	CategoryDto createCaty(CategoryDto categoryDto);
	
	//get all
	List<CategoryDto> getAllCaty();
	
	//get one
	public CategoryDto getCatyById(Integer categoryId);
	
	//update
	public CategoryDto updateCaty(CategoryDto categoryDto, Integer categoryId);
	
	//delete
	public void deleteCaty(Integer categoryId);
}
