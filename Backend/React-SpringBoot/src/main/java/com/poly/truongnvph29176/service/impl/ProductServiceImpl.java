package com.poly.truongnvph29176.service.impl;

import com.poly.truongnvph29176.dto.request.ProductRequest;
import com.poly.truongnvph29176.entity.Brand;
import com.poly.truongnvph29176.entity.Category;
import com.poly.truongnvph29176.entity.Product;
import com.poly.truongnvph29176.exception.DataNotFoundException;
import com.poly.truongnvph29176.repository.BrandRepository;
import com.poly.truongnvph29176.repository.CategoryRepository;
import com.poly.truongnvph29176.repository.ProductRepository;
import com.poly.truongnvph29176.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(ProductRequest productRequest) throws Exception {
        Brand existingBrand = brandRepository.findById(productRequest.getBrandId()).orElseThrow(() ->
                    new DataNotFoundException("Cannot find Brand with id = " + productRequest.getBrandId())
                );
        Category existingCategory = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() ->
                    new DataNotFoundException("Cannot find Category with id = " + productRequest.getCategoryId())
                );
        Product product = Product.builder()
                .name(productRequest.getName())
                .brand(existingBrand)
                .category(existingCategory)
                .build();
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Integer id) throws Exception {
        return productRepository.findById(id).orElseThrow(() ->
                    new DataNotFoundException("Cannot find Product with id = " +id)
                );
    }

    @Override
    public Product updateProduct(Integer id, ProductRequest productRequest) throws Exception {
        Product product = findProductById(id);
        if(product != null) {
            Brand existingBrand = brandRepository
                    .findById(productRequest.getBrandId()).orElseThrow(() ->
                            new DataNotFoundException("Cannot find Brand with id = " + productRequest.getBrandId())
            );
            Category existingCategory = categoryRepository
                    .findById(productRequest.getCategoryId()).orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find Category with id = " + productRequest.getCategoryId())
            );
            product.setName(productRequest.getName());
            product.setBrand(existingBrand);
            product.setCategory(existingCategory);
            return productRepository.save(product);
        }
        return null;
    }

    @Override
    public void deleteProduct(Integer id) throws Exception {
        Product product = findProductById(id);
        if(!product.getProductDetails().isEmpty()) {
            throw new RuntimeException("The product cannot be deleted because it is related to product_detail.");
        }
        productRepository.delete(product);
    }
}
