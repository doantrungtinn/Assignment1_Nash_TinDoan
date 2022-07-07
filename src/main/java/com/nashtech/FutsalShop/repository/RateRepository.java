package com.nashtech.FutsalShop.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nashtech.FutsalShop.model.Rate;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer> {
	List<Rate> findByProductIdAndCustomerStatusNot(String id, boolean status);

	List<Rate> findByProductIdAndCustomerStatusNot(Pageable pageable, String id, boolean status);

	int countByProductIdAndCustomerStatusNot(String id, boolean status);

	boolean existsByProductIdAndCustomerId(String prodId, int userId);
	
	boolean existsById(int id);

	@Query("select count(o) from Person p , Order o , Orderdetail o2, Product p2 \r\n"
			+ "where p.id = o.customers.id and o2.id.orderId = o.id and o2.id.productId = p2.id and p.status != false "
			+ "and p2.status != false and p2.id = ?1 and p.id = ?2")
	int checkUserOrdered(String prodId, int customerId);
}
