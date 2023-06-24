package com.sparksupport.productAndSales.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sparksupport.productAndSales.dto.Product;
import com.sparksupport.productAndSales.dto.Sale;
import com.sparksupport.productAndSales.repository.ProductRepository;
import com.sparksupport.productAndSales.repository.SaleRepository;



@Service
public class ProductService {

	@Autowired
	private  ProductRepository productRepository;
	@Autowired
    private  SaleRepository saleRepository;


//	public List<Product> getAllProducts(Pageable pageable) {
//	    Page<Product> productsPage = productRepository.findAll(pageable);
//	    return productsPage.getContent();
//	}
	public List<Product> getAllProducts(Pageable pageable) {
	    List<Product> productsList = productRepository.findAll(pageable).getContent();
	    System.out.println(productsList.size());
	    return productsList;
	}


    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product addProduct(Product product) {
        if (productRepository.existsByName(product.getName())) {
            throw new IllegalArgumentException("Product with the same name already exists");
        }else {
        return productRepository.save(product);}
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = getProductById(id);
        if (product != null) {
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantity(updatedProduct.getQuantity());
            return productRepository.save(product);
        }
        return null;
    }

    public boolean deleteProduct(Long id) {
        Product product = getProductById(id);
        if (product != null) {
            productRepository.delete(product);
            return true;
        }
        return false;
    }

    public BigDecimal getTotalRevenue() {
        List<Sale> sales = saleRepository.findAll();
        BigDecimal totalRevenue = BigDecimal.ZERO;
        for (Sale sale : sales) {
            BigDecimal productPrice = sale.getProduct().getPrice();
            Integer quantity = sale.getQuantity();
            //sum Product -- price * sales-- quantity 
            BigDecimal saleRevenue = productPrice.multiply(BigDecimal.valueOf(quantity));
            totalRevenue = totalRevenue.add(saleRevenue);
        }
        return totalRevenue;
    }

    public BigDecimal getRevenueByProduct(Long productId) {
        List<Sale> sales = saleRepository.findByProductId(productId);
        BigDecimal productRevenue = BigDecimal.ZERO;
        for (Sale sale : sales) {
            BigDecimal productPrice = sale.getProduct().getPrice();
            Integer quantity = sale.getQuantity();
            BigDecimal saleRevenue = productPrice.multiply(BigDecimal.valueOf(quantity));
            productRevenue = productRevenue.add(saleRevenue);
        }
        return productRevenue;
    }
    
    public boolean existsProductByName(String productName) {
        return productRepository.existsByName(productName);
    }
}
