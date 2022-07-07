package com.nashtech.FutsalShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.FutsalShop.model.Orderdetail.OrderDetailsKey;
import com.nashtech.FutsalShop.model.Orderimportdetail;

@Repository
public interface OrderImportDetailRepository extends JpaRepository<Orderimportdetail, OrderDetailsKey>{

}
