package com.tindoan.tshop.repository;

import com.tindoan.tshop.model.customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<customer, Integer> {
}
