package com.poly.truongnvph29176.controller;

import com.poly.truongnvph29176.dto.request.ProductDetailRequest;
import com.poly.truongnvph29176.dto.request.ProductImageRequest;
import com.poly.truongnvph29176.dto.response.ProductDetailResponse;
import com.poly.truongnvph29176.entity.Product;
import com.poly.truongnvph29176.entity.ProductDetail;
import com.poly.truongnvph29176.entity.ProductImage;
import com.poly.truongnvph29176.service.ProductDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/product-details")
public class ProductDetailController {
    private final ProductDetailService productDetailService;

    @GetMapping("")
    public ResponseEntity<?> getAllProductDetails() {
        List<ProductDetail> list = productDetailService.findAllProductDetails();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductDetailById(@PathVariable("id") Integer id) {
        try {
            ProductDetail productDetail = productDetailService.findProductDetailById(id);
            ProductDetailResponse productDetailResponse = ProductDetailResponse.builder()
                    .id(productDetail.getId())
                    .productId(productDetail.getId())
                    .colorId(productDetail.getColor().getId())
                    .sizeId(productDetail.getSize().getId())
                    .price(productDetail.getPrice())
                    .quantity(productDetail.getQuantity())
                    .productImages(productDetail.getProductImages())
                    .description(productDetail.getDescription())
                    .build();
            return ResponseEntity.ok(productDetailResponse);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createProductDetail(@Valid @RequestBody ProductDetailRequest productDetailRequest,
                                                 BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }else {
                ProductDetail productDetail = productDetailService.createProductDetail(productDetailRequest);
                return ResponseEntity.ok(productDetail);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductDetail(@PathVariable("id") Integer id,
                                                 @Valid @RequestBody ProductDetailRequest productDetailRequest,
                                                 BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }else {
                ProductDetail productDetail = productDetailService.updateProductDetail(id, productDetailRequest);
                return ResponseEntity.ok(productDetail);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductDetail(@PathVariable("id") Integer id) {
        productDetailService.deleteProductDetail(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    @PostMapping(value = "/uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@ModelAttribute("file")List<MultipartFile> files,
                                          @PathVariable("id") Integer id) {
        try {
            ProductDetail existingProductDetail = productDetailService.findProductDetailById(id);
            files = files == null ? new ArrayList<>() : files;
            if(files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
                return ResponseEntity.badRequest().body("You can only upload maximum 4 images");
            }
            List<ProductImage> productImages = new ArrayList<>();
            for(MultipartFile file : files) {
                if(file.getSize() == 0) {
                    continue;
                }
                // TODO Kiểm tra kích thước file và định dạng
                // TODO Kích thức > 10MB
                if(file.getSize() > 10 * 1024 * 1024) {
                    return ResponseEntity
                            .status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large! Maximum size id 10MB");
                }
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity
                            .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
                // TODO Lưu file và cập nhật thumbnail trong DTO
                String fileName = storeFile(file);
                ProductImage productImage = productDetailService
                        .createProductImage(existingProductDetail.getId(),
                                ProductImageRequest.builder()
                                        .imageUrl(fileName)
                                        .build()
                        );
                productImages.add(productImage);
            }
            return ResponseEntity.ok(productImages);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // TODO Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        // TODO Đường dẫn đến thư mục mà bạn muốn lưu file
        Path uploadDir = Paths.get("uploads");
        // TODO Kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // TODO Đường dẫn đầy đủ đến file
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // TODO Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
}
