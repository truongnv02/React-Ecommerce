package com.poly.truongnvph29176.service.impl;

import com.poly.truongnvph29176.dto.request.BrandRequest;
import com.poly.truongnvph29176.entity.Brand;
import com.poly.truongnvph29176.exception.DataNotFoundException;
import com.poly.truongnvph29176.repository.BrandRepository;
import com.poly.truongnvph29176.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public List<Brand> findAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand createBrand(BrandRequest brandRequest) {
        Brand brand = Brand.builder()
                .name(brandRequest.getName())
                .build();
        return brandRepository.save(brand);
    }

    @Override
    public Brand updateBrand(Integer id, BrandRequest brandRequest) throws Exception {
        Brand idBrand = findBrandById(id);
        idBrand.setName(brandRequest.getName());
        brandRepository.save(idBrand);
        return idBrand;
    }

    @Override
    public Brand findBrandById(Integer id) throws Exception {
        return brandRepository.findById(id).orElseThrow(() ->
                    new DataNotFoundException("Cannot find Brand with id = " + id)
                );
    }

    @Override
    public void deleteBrand(Integer id) {
        brandRepository.deleteById(id);
    }
}
