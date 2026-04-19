package com.example.test;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/hello")
	public Map<String, String> sayHello() {
		return Map.of("message", "Hello from Spring Boot 3 & Java 21!", "status", "success");
	}

	@PostMapping("/echo")
	public Map<String, Object> echoData(@RequestBody Map<String, Object> input) {
		return Map.of("received", input, "processedBy", "AWS Lambda");
	}

	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		Product created = productService.createProduct(product);

		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@GetMapping
	public ResponseEntity<List<Product>> findAllProducts() {

		return ResponseEntity.ok(productService.findAllProduct());

	}

	@GetMapping("/{productId}")
	public ResponseEntity<Product> findProductById(@PathVariable("productId") String productId) {

		Product product = productService.getProduct(productId);
		if (product == null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(product);
	}

	@PutMapping
	public ResponseEntity<Product> updateProduct(@PathVariable("productId") String productId,
			@RequestBody Product product) {

		Product updateProduct = productService.getProduct(productId);

		updateProduct.setName(product.getName());
		updateProduct.setDescription(product.getDescription());
		updateProduct.setPrice(product.getPrice());
		productService.updateProduct(productId,updateProduct);
		return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
	}

	@DeleteMapping("/{productId}")

	public ResponseEntity<Void> deleteProduct(@PathVariable("productId") String productId) {
		productService.deleteProductById(productId);
		return ResponseEntity.noContent().build();
	}

}
