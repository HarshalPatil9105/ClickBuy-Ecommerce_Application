package com.clickbuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clickbuy.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	 User findByEmail(String email);
}
