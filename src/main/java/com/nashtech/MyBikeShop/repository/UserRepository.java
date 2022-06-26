package com.nashtech.MyBikeShop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nashtech.MyBikeShop.model.user;

public interface UserRepository extends JpaRepository<user, Integer> {

	Optional<user> findByEmail(String email);

	Boolean existsByEmail(String email);
}
