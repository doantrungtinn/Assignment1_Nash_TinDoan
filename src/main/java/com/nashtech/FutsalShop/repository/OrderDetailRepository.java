package com.nashtech.FutsalShop.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.FutsalShop.model.orderdetail;
import com.nashtech.FutsalShop.model.orderdetail.OrderDetailsKey;

@Repository
public interface OrderDetailRepository extends JpaRepository<orderdetail, OrderDetailsKey> {
	Set<orderdetail> findByIdOrderId(int id);
}
