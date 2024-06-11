package com.nichoscode.sportyshoes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nichoscode.sportyshoes.model.Product;
import com.nichoscode.sportyshoes.model.User;
import com.nichoscode.sportyshoes.service.CartService;
import com.nichoscode.sportyshoes.service.ProductService;
import com.nichoscode.sportyshoes.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Controller
public class HomeController {
	
	
	private final ProductService productService;
	private final UserService userService;
	private BCryptPasswordEncoder bCrypPasswordEncoder;
	
	@Autowired
    private CartService cartService;

    @Autowired
    public HomeController(ProductService productService, UserService userService, BCryptPasswordEncoder bCrypPasswordEncoder) {
        this.productService = productService;
		this.userService = userService;
		this.bCrypPasswordEncoder = bCrypPasswordEncoder;
    }
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/shop")
    public String shop(Model model) {
		List<Product> productList = productService.getAllProducts();
	      model.addAttribute("products", productList);
	      model.addAttribute("cart", cartService.getCart());
        return "shop"; 
    }
      

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("cart", cartService.getCart());
        model.addAttribute("grandTotal", cartService.getGrandTotal());
        return "cart";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register"; 
    }
    
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user")User user, HttpServletRequest request) throws ServletException {
    	System.out.println("---------------------------------" + user);
		String password = user.getPassword();
		user.setPassword(bCrypPasswordEncoder.encode(password));
		userService.save(user);
		request.login(user.getEmail(), password);
		System.out.println("-------]]]]]]]]]]]]]]]]--------" + user);
		return "redirect:/shop";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "message", required = false) String message,
            Model model) {
			if (error != null) {
			model.addAttribute("errorMessage", message != null ? message : "Invalid username or password.");
			}
			return "login";
}
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
    
    @PostMapping("/shop/addToCart/{id}")
    public String addToCart(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product != null) {
            cartService.addProduct(product.get());
        }
        
        return "redirect:/shop";
    }

}
