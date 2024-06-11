package com.nichoscode.sportyshoes.controller;

import com.nichoscode.sportyshoes.enums.Category;
import com.nichoscode.sportyshoes.model.Product;
import com.nichoscode.sportyshoes.model.User;
import com.nichoscode.sportyshoes.service.ProductService;
import com.nichoscode.sportyshoes.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final ProductService productService;
	private final UserService userService;

	@Autowired
	public AdminController(ProductService productService, UserService userService) {
		this.productService = productService;
		this.userService = userService;
	}

	@GetMapping("/products")
	public String getAllProducts(@RequestParam(required = false) String name,
			@RequestParam(required = false) Category category, Model model) {
		List<Product> productList;
		if (name != null || category != null) {
			productList = productService.findByNameOrCategory(name, category);
		} else {
			productList = productService.getAllProducts();
		}

		model.addAttribute("products", productList);
		return "products";
	}

//    @GetMapping("/products")
//    public String getAllProducts(Model model) {
//        List<Product> productList = productService.getAllProducts();
//        model.addAttribute("products", productList);
//        return "products";
//    }

	@GetMapping("/products/delete/{id}")
	public String deleteProd(@PathVariable long id) {
		productService.deleteProduct(id);
		return "redirect:/admin/products";
	}

	@GetMapping("/products/create")
	public String showCreateProductForm(Model model) {
		model.addAttribute("product", new Product());
		return "addProduct"; //
	}

	@PostMapping("/products/create")
	public String addProduct(Product product) {
		productService.createProduct(product);
		return "redirect:/admin/products";
	}

	@GetMapping("/products/edit/{id}")
	public String showEditProductForm(@PathVariable long id, Model model) {
		Optional<Product> optionalProduct = productService.getProductById(id);
		System.out.println("================ " + optionalProduct.get());
		if (optionalProduct.isPresent()) {
			model.addAttribute("product", optionalProduct.get());
			return "editProduct"; // Name of the Thymeleaf template (edit_product.html)
		} else {
			// handle the case when the product is not found
			return "redirect:/admin/products";
		}

	}

	@PostMapping("/products/edit/{id}")
	public String updateProduct(@PathVariable long id, Product product) {
		productService.updateProduct(id, product);
		return "redirect:/admin/products";
	}

	@GetMapping("/users")
	public String getAllUsers(@RequestParam(required = false) String email, Model model) {
		List<User> userList = new ArrayList<>();
		if (email != null) {
			userList.add(userService.findByEmail(email));
		} else {
			userList = userService.findAll();
		}

		model.addAttribute("users", userList);
		return "users";
	}
	
	 @GetMapping("/report")
	    public String report() {
	        return "report"; // Return the report.html template
	    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
//        Product product = productService.getProductById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Product not found for this id :: " + id));
//        return ResponseEntity.ok().body(product);
//    }

//    @GetMapping("/category/{category}")
//    public List<Product> getProductsByCategory(@PathVariable Category category) {
//        return productService.getProductsByCategory(category);
//    }

//    @GetMapping("/name/{name}")
//    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
//        Product product = productService.getProductByName(name)
//                .orElseThrow(() -> new IllegalArgumentException("Product not found with name :: " + name));
//        return ResponseEntity.ok().body(product);
//    }

//    @GetMapping("/brand/{brand}")
//    public List<Product> getProductsByBrand(@PathVariable String brand) {
//        return productService.getProductsByBrand(brand);
//    }

//    @PostMapping
//    public Product createProduct(@RequestBody Product product) {
//        return productService.createProduct(product);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
//        Product updatedProduct = productService.updateProduct(id, productDetails);
//        return ResponseEntity.ok(updatedProduct);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
//        productService.deleteProduct(id);
//        return ResponseEntity.noContent().build();
//    }
}
