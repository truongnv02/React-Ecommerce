package com.poly.truongnvph29176.service.impl;

import com.poly.truongnvph29176.entity.Account;
import com.poly.truongnvph29176.model.UserCustomDetail;
import com.poly.truongnvph29176.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> findByEmail = accountRepository.findByEmail(username);
        return UserCustomDetail.builder().account(findByEmail.get()).build();
    }
}
