package com.clickbuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clickbuy.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	Category findByCategoryId(String category);
	

}
