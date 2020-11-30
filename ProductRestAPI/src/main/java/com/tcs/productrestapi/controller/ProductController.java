package com.tcs.productrestapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.productrestapi.exception.ResourceNotFoundException;
import com.tcs.productrestapi.model.Product;
import com.tcs.productrestapi.service.ProductService;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
	@Autowired
	ProductService productService;
	
	
	@GetMapping
	public List<Product> getProducts() {
		return productService.getProducts().get();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") int productId) throws ResourceNotFoundException {
		Product product = productService.getProductById(productId).orElseThrow(()-> new ResourceNotFoundException("Product not found"));
		
		return ResponseEntity.ok().body(product);
	}
	
	@PostMapping("/create")
	public Product createProduct(@RequestBody Product product) {
	
	  return 	productService.createProduct(product);
	}
	
	@DeleteMapping("/{id}")

	public String deleteProductById(@PathVariable int id) { 
		
		productService.deleteProduct(id);
		return "success";
	}
}