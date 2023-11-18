package com.poly.truongnvph29176.service;

import com.poly.truongnvph29176.dto.request.ProductRequest;
import com.poly.truongnvph29176.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProducts();
    Product createProduct(ProductRequest productRequest) throws Exception;
    Product findProductById(Integer id) throws Exception;
    Product updateProduct(Integer id, ProductRequest productRequest) throws Exception;
    void deleteProduct(Integer id) throws Exception;
}
