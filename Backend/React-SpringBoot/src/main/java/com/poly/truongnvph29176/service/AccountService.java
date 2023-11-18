package com.poly.truongnvph29176.service;

import com.poly.truongnvph29176.dto.request.LoginRequest;
import com.poly.truongnvph29176.dto.request.RegisterRequest;
import com.poly.truongnvph29176.dto.response.TokenResponse;
import com.poly.truongnvph29176.entity.Account;

public interface AccountService {
    Account register(RegisterRequest registerRequest);
    TokenResponse login(LoginRequest loginRequest);
}
