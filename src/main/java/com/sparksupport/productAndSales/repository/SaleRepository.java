package com.sparksupport.productAndSales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparksupport.productAndSales.dto.Product;
import com.sparksupport.productAndSales.dto.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
	
	List<Sale> findByProductId(Long productId);
}
