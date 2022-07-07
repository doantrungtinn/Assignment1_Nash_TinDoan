package com.nashtech.FutsalShop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nashtech.FutsalShop.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

	Boolean existsByEmail(String email);
}
