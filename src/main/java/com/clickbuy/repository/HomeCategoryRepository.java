
package com.clickbuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
 
import com.clickbuy.model.HomeCategory;

public interface HomeCategoryRepository extends JpaRepository<HomeCategory, Long> {

}
