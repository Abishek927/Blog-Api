package com.api.blog.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.blog.Payloads.ApiResponse;
import com.api.blog.Payloads.CategoryDTO;
import com.api.blog.Service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@Valid@RequestBody CategoryDTO categoryDTO) {
		CategoryDTO categoryDTO2 = this.categoryService.createCategory(categoryDTO);
		return new ResponseEntity<CategoryDTO>(categoryDTO2, HttpStatus.CREATED);

	}

	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("categoryId") Long cid,
			@Valid@RequestBody CategoryDTO categoryDTO) {
		CategoryDTO resultCategoryDTO = this.categoryService.updateCategory(cid, categoryDTO);
		return new ResponseEntity<CategoryDTO>(resultCategoryDTO, HttpStatus.OK);

	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId")Long categoryId){
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted successfully",true),HttpStatus.OK);
		
		
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> getAllCategory(){
		
		List<CategoryDTO> categoryDTOs=this.categoryService.getAllCategory();
		return new ResponseEntity<List<CategoryDTO>>(categoryDTOs,HttpStatus.OK);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("categoryId")Long categoryId){
		CategoryDTO categoryDTO=this.categoryService.getCategoryById(categoryId);
		return new ResponseEntity<CategoryDTO>(categoryDTO,HttpStatus.OK);
		}
	

}
