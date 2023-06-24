package com.sparksupport.productAndSales;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sparksupport.productAndSales.dto.Product;
import com.sparksupport.productAndSales.dto.Sale;
import com.sparksupport.productAndSales.service.ProductService;
import com.sparksupport.productAndSales.service.SaleService;

@SpringBootApplication
public class ProductAndSalesApplication {
	
    @Autowired
    private static ProductService productService;

    @Autowired
    private static SaleService saleService;
    
    

	public ProductAndSalesApplication(ProductService productService, SaleService saleService) {
		super();
		this.productService = productService;
		this.saleService = saleService;
	}



	public static void main(String[] args) throws ParseException {
		SpringApplication.run(ProductAndSalesApplication.class, args);
	
	 Product product1 = new Product();
     product1.setName("HAMAM SOUP");
     product1.setDescription("250G");
     product1.setPrice(BigDecimal.valueOf(100.10));
     product1.setQuantity(10);

     Product product2 = new Product();
     product2.setName("APPLE");
     product2.setDescription("1KG");
     product2.setPrice(BigDecimal.valueOf(200.00));
     product2.setQuantity(5);

     Sale sale1 = new Sale();
     sale1.setProduct(product1);
     sale1.setQuantity(2);
     String date1="2022/01/23";
     SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
     Date correctDate=dateFormat.parse(date1);
     sale1.setSaleDate(correctDate);

     Sale sale2 = new Sale();
     sale2.setProduct(product2);
     sale2.setQuantity(3);
     sale2.setSaleDate(new Date());

     ProductAndSalesApplication application = new ProductAndSalesApplication(productService, saleService);
     application.addProduct(product1);
     application.addProduct(product2);
     application.addSale(sale1);
     application.addSale(sale2);
 }

 public void addProduct(Product product) {
     if (!productService.existsProductByName(product.getName())) {
         productService.addProduct(product);
         System.out.println("Product added successfully: " + product.getName());
     } else {
         System.out.println("Product with the same name already exists. Skipping: " + product.getName());
     }
 }

 public void addSale(Sale sale) {
     saleService.addSale(sale);
     System.out.println("Sale added successfully: " + sale.getId());
 }
}

