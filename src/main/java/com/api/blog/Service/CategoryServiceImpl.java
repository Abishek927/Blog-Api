package com.api.blog.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.blog.Exception.ResourceNotFoundException;
import com.api.blog.Model.Category;
import com.api.blog.Payloads.CategoryDTO;
import com.api.blog.Repositories.CategoryRepositories;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepositories categoryRepositories;

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Category category=this.modelMapper.map(categoryDTO,Category.class);
		Category category2=this.categoryRepositories.save(category);		
		CategoryDTO resultCategoryDTO=this.modelMapper.map(category2,CategoryDTO.class);
		return resultCategoryDTO;
	}

	@Override
	public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
		Category category=this.categoryRepositories.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Id", categoryId));
		category.setCategoryTitle(categoryDTO.getCatTitle());
		category.setCategoryDescription(categoryDTO.getCatDescription());
		this.categoryRepositories.save(category);
		
		return categoryDTO;
	}

	@Override
	public List<CategoryDTO> getAllCategory() {
		List<Category> categories=this.categoryRepositories.findAll();
		
		List<CategoryDTO> categoryDTOs=categories.stream().map(category->this.modelMapper.map(category,CategoryDTO.class)).collect(Collectors.toList());
		
		return categoryDTOs;
	}

	@Override
	public void deleteCategory(Long categoryId) {
		Category category=this.categoryRepositories.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Id", categoryId));
		this.categoryRepositories.delete(category);
		
	}

	@Override
	public CategoryDTO getCategoryById(Long categoryId) {
		Category category=this.categoryRepositories.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Id", categoryId));
		
		CategoryDTO categoryDTO=this.modelMapper.map(category,CategoryDTO.class);
		
		return categoryDTO;
		
	}

}
