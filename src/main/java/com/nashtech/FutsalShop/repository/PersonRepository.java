package com.nashtech.FutsalShop.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.FutsalShop.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
	List<Person> findByRoleAndStatusNot(Pageable pageable, String role, boolean status);

	List<Person> findByRoleNotAndStatusNot(Pageable pageable, String role, boolean status);

	List<Person> findByEmailIgnoreCase(String email);

	Person findByEmail(String email);

	Boolean existsByEmail(String email);

	int countByRoleAndStatusNot(String role, boolean status);

	int countByRoleNotAndStatusNot(String role, boolean status);
	
	@Query("SELECT p FROM Person p WHERE (UPPER(p.fullname) LIKE %?1%) and (p.role = ?2) and (p.status != false) ORDER BY p.id DESC")
	List<Person> searchPerson(String keyword, String role);
	
	@Query("SELECT p FROM Person p WHERE (UPPER(p.fullname) LIKE %?1%) and (p.role != ?2) and (p.status != false) ORDER BY p.id DESC")
	List<Person> searchPersonRoleNot(String keyword, String role);

	@Transactional
	@Modifying
	@Query(value = "\\copy persons(email,password,fullname,dob,gender,address,phonenumber,role,status)"
			+ " to ?1 DELIMITER ',' CSV HEADER;", nativeQuery = true)
	void toCSVFile( String path);
	
	@Transactional
	@Modifying
	@Query(value = "TRUNCATE TABLE persons \r\n"
			+ "RESTART IDENTITY;\\copy persons(email,password,fullname,dob,gender,address,phonenumber,role,status)"
			+ " from 'D:\\WORK\\persons_client.csv' DELIMITER ',' CSV HEADER;", nativeQuery = true)
	void importCSVFile(@Param("path") String path);
}
