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
public class OrderDetailRequest {
    private Integer orderId;
    private Integer productDetailId;
    private Float price;
    private Integer quantity;
    private Float totalMoney;
}
