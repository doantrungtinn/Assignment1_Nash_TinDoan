package com.tindoan.tshop.repository;

import com.tindoan.tshop.model.role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<role, String> {
}