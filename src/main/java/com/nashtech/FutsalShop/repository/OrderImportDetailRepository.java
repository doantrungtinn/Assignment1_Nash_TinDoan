package com.nashtech.FutsalShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.FutsalShop.model.orderdetail.OrderDetailsKey;
import com.nashtech.FutsalShop.model.orderimportdetail;

@Repository
public interface OrderImportDetailRepository extends JpaRepository<orderimportdetail, OrderDetailsKey>{

}
