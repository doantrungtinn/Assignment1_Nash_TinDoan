package com.tindoan.tshop.repository;

import com.tindoan.tshop.model.product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<product, String> {
}
