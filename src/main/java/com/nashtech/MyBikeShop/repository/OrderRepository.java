package com.nashtech.MyBikeShop.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nashtech.MyBikeShop.model.order;

@Repository
public interface OrderRepository extends JpaRepository<order, Integer> {
	List<order> findByCustomersEmail(Pageable pageable, String email);

	List<order> findByCustomersId(Pageable pageable, int id);

	List<order> findByStatus(Pageable pageable, int status);

	List<order> findByOrderDetailsIdProductIdAndCustomersId(String prodId, int customerId);

	long countByCustomersEmail(String email);

	long countByStatus(int status);

	boolean existsById(int id);

	@Query(value = "select SUM(o2.amount*o2.unitprice) from orderbill o , orderdetails o2 \r\n"
			+ "where o.id =o2.orderid and EXTRACT(MONTH FROM o.timebought) = :month and EXTRACT(YEAR FROM o.timebought) = :year", nativeQuery = true)
	Float profitByMonth(@Param("month") Integer month, @Param("year") Integer year);

	@Query("SELECT o FROM order o WHERE (UPPER(o.customers.fullname) LIKE %?1%) ORDER BY o.timebought DESC")
	List<order> searchOrderByCustomer(String keyword);

	@Query("SELECT o FROM order o WHERE (UPPER(o.customers.fullname) LIKE %?1%) and o.status = ?2 ORDER BY o.timebought DESC")
	List<order> searchOrderByStatusAndCustomer(String keyword, int status);

	@Query("SELECT o FROM order o WHERE EXTRACT(MONTH FROM o.timebought) <= :month and EXTRACT(YEAR FROM o.timebought) <= :year"
			+ " ORDER BY o.timebought ASC")
	List<order> getOrderFromMonth(@Param("month") Integer month, @Param("year") Integer year);

	@Query("select Max(o.id) from order o ")
	int findFirstByIdOrderByIdDesc();
	
	@Query(value = "select SUM(o2.unitprice*o2.amount)/SUM(o2.amount) " 
			+ "from orderbill o, orderdetails o2 "
			+ "where o.id = o2.orderid and "
			+ "o2.productid = :prodId and EXTRACT(MONTH FROM o.timebought) <= :month and EXTRACT(YEAR FROM o.timebought) <= :year "
			+ "", nativeQuery = true)
	Double avgPriceSoldOfProd(@Param("prodId") String prodId, @Param("month") Integer month, @Param("year") Integer year);
}
