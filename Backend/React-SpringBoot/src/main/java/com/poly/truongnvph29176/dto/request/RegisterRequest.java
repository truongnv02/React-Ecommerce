package com.poly.truongnvph29176.dto.request;

import com.poly.truongnvph29176.enums.RoleEnums;
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
public class RegisterRequest {
    private String fullName;

    private String password;

    private String email;

    private RoleEnums role;
}
