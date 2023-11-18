package com.poly.truongnvph29176.repository;

import com.poly.truongnvph29176.entity.Role;
import com.poly.truongnvph29176.enums.RoleEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleEnums(RoleEnums roleEnums);
}
