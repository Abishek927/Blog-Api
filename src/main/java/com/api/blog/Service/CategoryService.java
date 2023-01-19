package com.api.blog.Service;

import java.util.List;

import com.api.blog.Payloads.CategoryDTO;

public interface CategoryService {
	
	public CategoryDTO createCategory(CategoryDTO categoryDTO);
	
	public CategoryDTO updateCategory(Long categoryId,CategoryDTO categoryDTO);
	
	public List<CategoryDTO> getAllCategory();
	
	public void deleteCategory(Long categoryId);
	
	public CategoryDTO getCategoryById(Long categoryId);

}
