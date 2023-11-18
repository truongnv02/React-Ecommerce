package com.poly.truongnvph29176.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDetailRequest {
    private Integer productId;
    private Integer colorId;
    private Integer sizeId;
    private Float price;
    private Integer quantity;
    private String imageUrl;
    private String description;
}
