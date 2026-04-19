package com.example.test;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Product createProduct(Product product) {

		product.setProductId(UUID.randomUUID().toString());
		return productRepository.saveProduct(product);
	}

	public Product getProduct(String productId) {
		return productRepository.findById(productId);
	}

	public List<Product> findAllProduct() {
		return productRepository.findAllProduct();
	}

	public Product updateProduct(String productId, Product product) {

		product.setProductId(productId);
		return productRepository.saveProduct(product);
	}

	public void deleteProductById(String productId) {
		productRepository.deleteproductById(productId);
	}

}
