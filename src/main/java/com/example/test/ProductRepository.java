package com.example.test;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
public class ProductRepository {

	private final DynamoDbTable<Product> productTable;

	public ProductRepository(DynamoDbEnhancedClient dbEnhancedClient) {
		this.productTable = dbEnhancedClient.table("Products", TableSchema.fromBean(Product.class));
	}

	public Product saveProduct(Product product) {

		productTable.putItem(product);
		return product;

	}

	public Product findById(String productId) {

		Key key = Key.builder().partitionValue(productId).build();

		return productTable.getItem(key);

	}

	public List<Product> findAllProduct() {

		return productTable.scan().items().stream().collect(Collectors.toList());

	}

	public void deleteproductById(String productId) {
		Key key = Key.builder().partitionValue(productId).build();
		productTable.deleteItem(key);
	}

}
