package com.poly.truongnvph29176.service.impl;

import com.poly.truongnvph29176.dto.request.LoginRequest;
import com.poly.truongnvph29176.dto.request.RegisterRequest;
import com.poly.truongnvph29176.dto.response.TokenResponse;
import com.poly.truongnvph29176.entity.Account;
import com.poly.truongnvph29176.entity.Role;
import com.poly.truongnvph29176.jwt.JwtService;
import com.poly.truongnvph29176.model.UserCustomDetail;
import com.poly.truongnvph29176.repository.AccountRepository;
import com.poly.truongnvph29176.repository.RoleRepository;
import com.poly.truongnvph29176.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public Account register(RegisterRequest registerRequest) {
        Optional<Account> existingEmail = accountRepository.findByEmail(registerRequest.getEmail());
        if(existingEmail.isPresent()) {
            throw new RuntimeException("Email đã tồn tại");
        }
        Optional<Role> findByRole = roleRepository.findByRoleEnums(registerRequest.getRole());
        Account account = Account.builder()
                .fullName(registerRequest.getFullName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .role(findByRole.get())
                .build();
        return accountRepository.save(account);

    }

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword())
        );
        Optional<Account> findByAccount = accountRepository.findByEmail(loginRequest.getEmail());
        String token = jwtService.generateToken(new UserCustomDetail(findByAccount.get()));
        return TokenResponse.builder()
                .token(token)
                .role(findByAccount.get().getRole().getRoleEnums().name())
                .build();
    }
}
