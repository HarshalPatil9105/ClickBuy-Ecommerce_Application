package com.clickbuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clickbuy.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

	Address save(Address pickupAddress);
	

}
