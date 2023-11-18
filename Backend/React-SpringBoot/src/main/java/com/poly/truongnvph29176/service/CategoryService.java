package com.poly.truongnvph29176.service;

import com.poly.truongnvph29176.dto.request.CategoryRequest;
import com.poly.truongnvph29176.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategory();
    Category createCategory(CategoryRequest categoryRequest);
    Category findCategoryById(Integer id) throws Exception;
    Category updateCategory(Integer id, CategoryRequest categoryRequest) throws Exception;
    void deleteCategory(Integer id);
}
