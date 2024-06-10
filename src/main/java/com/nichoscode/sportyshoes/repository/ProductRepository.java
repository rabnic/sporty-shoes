package com.nichoscode.sportyshoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nichoscode.sportyshoes.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
