package com.sparksupport.productAndSales.productController;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.sparksupport.productAndSales.dto.Product;
import com.sparksupport.productAndSales.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            List<Product> products = productService.getAllProducts(pageRequest);
            System.out.println(products.size()+"Size");
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
        	logger.error("Failed to read product", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            if (product != null) {
                return new ResponseEntity<>(product, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
        	logger.error("Failed to read by id product", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        try {
            product.setId(null);
            Product addedProduct = productService.addProduct(product);
            logger.debug("Product added successfully", addedProduct);
            return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to add product: " + e.getMessage());
            return new ResponseEntity<>("Duplicate product name please check the name", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Failed to add product", e);
            return new ResponseEntity<>("Failed to add product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        try {
            Product product = productService.updateProduct(id, updatedProduct);
            if (product != null) {
                return new ResponseEntity<>(product, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
        	logger.error("Failed to update product", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            boolean isDeleted = productService.deleteProduct(id);
            if (isDeleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
        	logger.error("Failed to delete product", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/revenue")
    public ResponseEntity<BigDecimal> getTotalRevenue() {
        try {
            BigDecimal totalRevenue = productService.getTotalRevenue();
            return new ResponseEntity<>(totalRevenue, HttpStatus.OK);
        } catch (Exception e) {
        	logger.error("Failed to get ALL  Revenue ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/revenue/{id}")
    public ResponseEntity<BigDecimal> getRevenueByProduct(@PathVariable Long id) {
        try {
            BigDecimal productRevenue = productService.getRevenueByProduct(id);
            return new ResponseEntity<>(productRevenue, HttpStatus.OK);
        } catch (Exception e) {
        	logger.error("Failed to get ALL  Revenue by ID", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
    	System.out.println("HIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
        return new ResponseEntity<>("Hi", HttpStatus.OK);
    }
}
