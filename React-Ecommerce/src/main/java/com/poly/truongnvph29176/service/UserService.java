package com.poly.truongnvph29176.service;

import com.poly.truongnvph29176.entity.User;

public interface UserService {
    User findUserById(Long id) throws Exception;
    User findUserProfileByJwt(String jwt) throws Exception;
}
