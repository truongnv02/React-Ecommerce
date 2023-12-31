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
public class OrderRequest {
    private Integer accountId;
    private String fullName;
    private String phoneNumber;
    private String address;
    private Float totalMoney;
    private String paymentMethod;
}
