package com.nashtech.FutsalShop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nashtech.FutsalShop.model.user;

public interface UserRepository extends JpaRepository<user, Integer> {

	Optional<user> findByEmail(String email);

	Boolean existsByEmail(String email);
}
