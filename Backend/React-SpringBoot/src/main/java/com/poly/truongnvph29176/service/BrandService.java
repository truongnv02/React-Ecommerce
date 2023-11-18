package com.poly.truongnvph29176.service;

import com.poly.truongnvph29176.dto.request.BrandRequest;
import com.poly.truongnvph29176.entity.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> findAllBrands();
    Brand createBrand(BrandRequest brandRequest);
    Brand updateBrand(Integer id, BrandRequest brandRequest) throws Exception;
    Brand findBrandById(Integer id) throws Exception;
    void deleteBrand(Integer id);
}
