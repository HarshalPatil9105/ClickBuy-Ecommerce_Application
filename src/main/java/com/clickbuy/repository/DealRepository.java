package com.clickbuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clickbuy.model.Deal;

public interface DealRepository extends JpaRepository<Deal, Long> {

}