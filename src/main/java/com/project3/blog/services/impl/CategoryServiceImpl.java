package com.project3.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project3.blog.entities.Category;
import com.project3.blog.exceptions.ResourceNotFoundException;
import com.project3.blog.payloads.CategoryDto;
import com.project3.blog.repositories.CategoryRepo;
import com.project3.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public CategoryDto createCaty(CategoryDto categoryDto) {
		Category caty=this.modelMapper.map(categoryDto, Category.class);
		Category addedCaty=this.categoryRepo.save(caty);
		
		return this.modelMapper.map(addedCaty, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCaty() {
		List<Category>catyList= this.categoryRepo.findAll();
		return catyList.stream()
				.map((caty)->this.modelMapper.map(caty, CategoryDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public CategoryDto getCatyById(Integer categoryId) {
		Category caty=this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		return this.modelMapper.map(caty, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCaty(CategoryDto categoryDto, Integer categoryId) {
		Category caty=this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		caty.setCategoryTitle(categoryDto.getCategoryTitle());
		caty.setCategoryDesc(categoryDto.getCategoryDesc());
		
		Category updatedCaty=this.categoryRepo.save(caty);
		
		return this.modelMapper.map(updatedCaty, CategoryDto.class);
	}

	@Override
	public void deleteCaty(Integer categoryId) {
		Category caty=this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		this.categoryRepo.deleteById(categoryId);
	}

}
