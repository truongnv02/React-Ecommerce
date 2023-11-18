package com.poly.truongnvph29176.service;

import com.poly.truongnvph29176.dto.request.ProductDetailRequest;
import com.poly.truongnvph29176.dto.request.ProductImageRequest;
import com.poly.truongnvph29176.dto.response.ProductDetailResponse;
import com.poly.truongnvph29176.entity.ProductDetail;
import com.poly.truongnvph29176.entity.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductDetailService {
    List<ProductDetail> findAllProductDetails();
    Page<ProductDetailResponse> getAllProductDetail(String keyword, Integer categoryId,
                                                    Integer colorId, Integer brandId,
                                                    Integer sizeId, Pageable pageable);
    ProductDetail createProductDetail(ProductDetailRequest productDetailRequest) throws Exception;
    ProductDetail findProductDetailById(Integer id) throws Exception;
    ProductDetail updateProductDetail(Integer id, ProductDetailRequest productDetailRequest) throws Exception;
    void deleteProductDetail(Integer id);
    ProductImage createProductImage(Integer id, ProductImageRequest productImageRequest) throws Exception;
}
