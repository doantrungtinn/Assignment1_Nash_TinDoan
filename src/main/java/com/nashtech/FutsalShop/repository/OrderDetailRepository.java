package com.nashtech.FutsalShop.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.FutsalShop.model.Orderdetail;
import com.nashtech.FutsalShop.model.Orderdetail.OrderDetailsKey;

@Repository
public interface OrderDetailRepository extends JpaRepository<Orderdetail, OrderDetailsKey> {
	Set<Orderdetail> findByIdOrderId(int id);
}
