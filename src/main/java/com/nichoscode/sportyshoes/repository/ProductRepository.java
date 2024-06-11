package com.nichoscode.sportyshoes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nichoscode.sportyshoes.enums.Category;
import com.nichoscode.sportyshoes.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query(value = "select p from Product p where p.category=:category")
	List<Product> findAllByCategory(@Param("category") Category category);

	@Query(value = "select p from Product p where p.name=:name")
	Optional<Product> findByName(String name);
	
	@Query(value = "select p from Product p where p.brand=:brand")
	List<Product> findAllByBrand(@Param("brand") String brand);
	
	@Query("SELECT p FROM Product p WHERE  p.category = :category OR  p.name = :name")
    List<Product> findByNameOrCategory(@Param("name") String name, @Param("category") Category category);

}
