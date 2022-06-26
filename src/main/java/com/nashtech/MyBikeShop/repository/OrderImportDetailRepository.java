package com.nashtech.MyBikeShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.MyBikeShop.model.orderdetail.OrderDetailsKey;
import com.nashtech.MyBikeShop.model.orderimportdetail;

@Repository
public interface OrderImportDetailRepository extends JpaRepository<orderimportdetail, OrderDetailsKey>{

}
