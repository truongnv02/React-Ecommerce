package com.poly.truongnvph29176.service.impl;

import com.poly.truongnvph29176.dto.request.CategoryRequest;
import com.poly.truongnvph29176.entity.Category;
import com.poly.truongnvph29176.exception.DataNotFoundException;
import com.poly.truongnvph29176.repository.CategoryRepository;
import com.poly.truongnvph29176.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(CategoryRequest categoryRequest) {
        Category category = Category.builder()
                .name(categoryRequest.getName())
                .build();
        return categoryRepository.save(category);
    }

    @Override
    public Category findCategoryById(Integer id) throws Exception {
        return categoryRepository.findById(id).orElseThrow(() ->
                    new DataNotFoundException("Cannot find Category with id = " + id)
                );
    }

    @Override
    public Category updateCategory(Integer id, CategoryRequest categoryRequest) throws Exception {
        Category idCategory = findCategoryById(id);
        idCategory.setName(categoryRequest.getName());
        categoryRepository.save(idCategory);
        return idCategory;
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }
}
