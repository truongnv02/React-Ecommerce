package com.poly.truongnvph29176.service.impl;

import com.poly.truongnvph29176.dto.request.ProductDetailRequest;
import com.poly.truongnvph29176.dto.request.ProductImageRequest;
import com.poly.truongnvph29176.dto.response.ProductDetailResponse;
import com.poly.truongnvph29176.entity.Color;
import com.poly.truongnvph29176.entity.Product;
import com.poly.truongnvph29176.entity.ProductDetail;
import com.poly.truongnvph29176.entity.ProductImage;
import com.poly.truongnvph29176.entity.Size;
import com.poly.truongnvph29176.exception.DataNotFoundException;
import com.poly.truongnvph29176.repository.ColorRepository;
import com.poly.truongnvph29176.repository.ProductDetailRepository;
import com.poly.truongnvph29176.repository.ProductImageRepository;
import com.poly.truongnvph29176.repository.ProductRepository;
import com.poly.truongnvph29176.repository.SizeRepository;
import com.poly.truongnvph29176.service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {
    private final ProductDetailRepository productDetailRepository;
    private final ProductRepository productRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public List<ProductDetail> findAllProductDetails() {
        return productDetailRepository.findAll();
    }

    @Override
    public Page<ProductDetailResponse> getAllProductDetail(String keyword, Integer categoryId,
                                                           Integer colorId, Integer brandId,
                                                           Integer sizeId, Pageable pageable) {
        return null;
    }

    @Override
    public ProductDetail createProductDetail(ProductDetailRequest productDetailRequest) throws Exception {
        Product existingProduct = productRepository
                .findById(productDetailRequest.getProductId()).orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find Product with id = " + productDetailRequest.getProductId())
                );
        Color existingColor = colorRepository
                .findById(productDetailRequest.getColorId()).orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find Color with id = " + productDetailRequest.getColorId())
                );
        Size existingSize = sizeRepository
                .findById(productDetailRequest.getSizeId()).orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find Size with id = " + productDetailRequest.getSizeId())
                );
        ProductDetail productDetail = ProductDetail.builder()
                .product(existingProduct)
                .color(existingColor)
                .size(existingSize)
                .price(productDetailRequest.getPrice())
                .quantity(productDetailRequest.getQuantity())
                .imageUrl(productDetailRequest.getImageUrl())
                .description(productDetailRequest.getDescription())
                .build();
        return productDetailRepository.save(productDetail);
    }

    @Override
    public ProductDetail findProductDetailById(Integer id) throws Exception {
        return productDetailRepository.findById(id).orElseThrow(() ->
                    new DataNotFoundException("Cannot find Product Detail with id = " + id)
                );
    }

    @Override
    public ProductDetail updateProductDetail(Integer id,
                                             ProductDetailRequest productDetailRequest) throws Exception {
        ProductDetail productDetail = findProductDetailById(id);
        if(productDetail != null) {
            Product existingProduct = productRepository
                    .findById(productDetailRequest.getProductId()).orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find Product with id = " + productDetailRequest.getProductId())
                    );
            Color existingColor = colorRepository
                    .findById(productDetailRequest.getColorId()).orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find Color with id = " + productDetailRequest.getColorId())
                    );
            Size existingSize = sizeRepository
                    .findById(productDetailRequest.getSizeId()).orElseThrow(() ->
                            new DataNotFoundException(
                                    "Cannot find Size with id = " + productDetailRequest.getSizeId())
                    );
            productDetail.setProduct(existingProduct);
            productDetail.setColor(existingColor);
            productDetail.setSize(existingSize);
            productDetail.setPrice(productDetailRequest.getPrice());
            productDetail.setQuantity(productDetailRequest.getQuantity());
            productDetail.setImageUrl(productDetailRequest.getImageUrl());
            productDetail.setDescription(productDetailRequest.getDescription());
            productDetailRepository.save(productDetail);
        }
        return null;
    }

    @Override
    public void deleteProductDetail(Integer id) {
        productDetailRepository.deleteById(id);
    }

    @Override
    public ProductImage createProductImage(Integer id, ProductImageRequest productImageRequest) throws Exception {
        ProductDetail productDetail = productDetailRepository.findById(id).orElseThrow(() ->
                    new DataNotFoundException(
                            "Cannot find Product Detail with id = " + productImageRequest.getImageUrl())
                );
        ProductImage productImage = ProductImage.builder()
                .productDetail(productDetail)
                .imageUrl(productImageRequest.getImageUrl())
                .build();
        // TODO Không insert quá 5 ảnh trên 1 sản phẩm
        int size = productImageRepository.findByProductDetailId(id).size();
        if(size > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParameterException("Number of images must be <= 4");
        }
        return productImageRepository.save(productImage);
    }
}
