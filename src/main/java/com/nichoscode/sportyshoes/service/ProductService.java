package com.nichoscode.sportyshoes.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.nichoscode.sportyshoes.model.Product;
import com.nichoscode.sportyshoes.repository.ProductRepository;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found for this id :: " + id));
        product.setName(productDetails.getName());
        product.setBrand(productDetails.getBrand());
        product.setPrice(productDetails.getPrice());
        product.setImageLink(productDetails.getImageLink());
        product.setCategory(productDetails.getCategory());
        
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found for this id :: " + id));
        productRepository.delete(product);
    }
}

