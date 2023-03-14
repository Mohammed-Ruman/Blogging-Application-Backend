package com.blog.userservice;

import java.util.List;

import com.blog.payloads.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);

	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

	CategoryDto getCategory(Integer categoryId);

	void deleteCategory(Integer categoryId);

	List<CategoryDto> getCategories();

}
