package com.sparksupport.productAndSales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparksupport.productAndSales.dto.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	boolean existsByName(String name);

}
