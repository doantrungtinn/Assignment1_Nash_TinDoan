package com.nashtech.FutsalShop.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nashtech.FutsalShop.model.Orderimport;

@Repository
public interface OrderImportRepository extends JpaRepository<Orderimport, Integer> {
	List<Orderimport> findByStatusNot(Pageable pageable, boolean status);
	
	int  countByStatusNot(boolean status);
	
	boolean existsById(int id);
	
	List<Orderimport> findByOrderImportDetailsIdProductIdAndStatusNot(Sort sort, String prodId, boolean status);
	
	@Query("select Max(o.id) from Orderimport o ")
	int findFirstByIdOrderByIdDesc();
	
	@Query(value = "select SUM(o2.amount*o2.price) "
			+ "from orderimport o, orderimportdetails o2 "
			+ "where o.id =o2.orderimportid and "
			+ "EXTRACT(MONTH FROM o.timeimport) = :month and EXTRACT(YEAR FROM o.timeimport) = :year", nativeQuery = true)
	Float purchaseCostByMonth(@Param("month") Integer month, @Param("year") Integer year);

	@Query("SELECT o FROM Orderimport o WHERE  (UPPER(o.employee.fullname) LIKE %?1%) ORDER BY o.timeimport DESC")
	List<Orderimport> searchImportByEmployee(String keyword);
	
	@Query(value = "select SUM(o2.price*o2.amount)/SUM(o2.amount) " 
			+ "from orderimport o, orderimportdetails o2 "
			+ "where o.id = o2.orderimportid and "
			+ "o2.productid = :prodId and EXTRACT(MONTH FROM o.timeimport) <= :month and EXTRACT(YEAR FROM o.timeimport) <= :year "
			+ "", nativeQuery = true)
	Double avgCostImportOfProd(@Param("prodId") String prodId, @Param("month") Integer month, @Param("year") Integer year);

	
}
