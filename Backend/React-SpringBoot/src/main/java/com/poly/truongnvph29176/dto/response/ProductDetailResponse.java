package com.poly.truongnvph29176.dto.response;

import com.poly.truongnvph29176.entity.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetailResponse {
    private Integer id;
    private Integer productId;
    private Integer colorId;
    private Integer sizeId;
    private Float price;
    private Integer quantity;
    private List<ProductImage> productImages;
    private String description;
}
