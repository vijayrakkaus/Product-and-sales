package com.sparksupport.productAndSales.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sparksupport.productAndSales.dto.Product;
import com.sparksupport.productAndSales.dto.Sale;
import com.sparksupport.productAndSales.repository.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository saleRepository;

	public Sale addSale(Sale sale) {
		return saleRepository.save(sale);
	}

	public List<Sale> getAllSales() {
		return saleRepository.findAll();
	}

	public Sale getSaleById(Long id) {
		Optional<Sale> optionalSale = saleRepository.findById(id);
		return optionalSale.orElse(null);
	}

	public Sale updateSale(Long id, Sale updatedSale) {
		Optional<Sale> optionalSale = saleRepository.findById(id);
		if (optionalSale.isPresent()) {
			Sale sale = optionalSale.get();
			sale.setProduct(updatedSale.getProduct());
			sale.setQuantity(updatedSale.getQuantity());
			sale.setSaleDate(updatedSale.getSaleDate());
			return saleRepository.save(sale);
		}
		return null;
	}

	public boolean deleteSale(Long id) {
		if (saleRepository.existsById(id)) {
			saleRepository.deleteById(id);
			return true;
		}
		return false;
	}
}

