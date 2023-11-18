package com.poly.truongnvph29176.controller;

import com.poly.truongnvph29176.dto.request.BrandRequest;
import com.poly.truongnvph29176.dto.response.BrandResponse;
import com.poly.truongnvph29176.entity.Brand;
import com.poly.truongnvph29176.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/brands")
public class BrandController {
    private final BrandService brandService;

    @GetMapping("")
    public ResponseEntity<?> getAllBrands() {
        List<Brand> list = brandService.findAllBrands();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Integer id) {
        try {
            Brand brand = brandService.findBrandById(id);
            BrandResponse brandResponse = BrandResponse.builder()
                    .id(brand.getId())
                    .name(brand.getName())
                    .build();
            return ResponseEntity.ok(brandResponse);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createBrand(@Valid @RequestBody BrandRequest brandRequest,
                                         BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }else {
                Brand brand = brandService.createBrand(brandRequest);
                return ResponseEntity.ok(brand);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable("id") Integer id,
                                         @Valid @RequestBody BrandRequest brandRequest,
                                         BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }else {
                Brand brand = brandService.updateBrand(id, brandRequest);
                return ResponseEntity.ok(brand);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable("id") Integer id) {
        brandService.deleteBrand(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
