package com.nashtech.MyBikeShop.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nashtech.MyBikeShop.model.rate;

@Repository
public interface RateRepository extends JpaRepository<rate, Integer> {
	List<rate> findByProductIdAndCustomerStatusNot(String id, boolean status);

	List<rate> findByProductIdAndCustomerStatusNot(Pageable pageable, String id, boolean status);

	int countByProductIdAndCustomerStatusNot(String id, boolean status);

	boolean existsByProductIdAndCustomerId(String prodId, int userId);
	
	boolean existsById(int id);

	@Query("select count(o) from person p , order o , orderdetail o2, product p2 \r\n"
			+ "where p.id = o.customers.id and o2.id.orderId = o.id and o2.id.productId = p2.id and p.status != false "
			+ "and p2.status != false and p2.id = ?1 and p.id = ?2")
	int checkUserOrdered(String prodId, int customerId);
}
