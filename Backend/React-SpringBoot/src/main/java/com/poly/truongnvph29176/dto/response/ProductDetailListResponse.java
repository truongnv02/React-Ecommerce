package com.poly.truongnvph29176.dto.response;

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
public class ProductDetailListResponse {
    private List<ProductDetailResponse> productDetailResponses;
    private Integer totalPages;
}
