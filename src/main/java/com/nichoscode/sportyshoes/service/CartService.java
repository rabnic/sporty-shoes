package com.nichoscode.sportyshoes.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.nichoscode.sportyshoes.model.CartItem;
import com.nichoscode.sportyshoes.model.Product;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartService {
    private List<CartItem> cart = new ArrayList<>();

    public void addProduct(Product product) {
        for (CartItem item : cart) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        cart.add(new CartItem(product, 1));
    }

    public List<CartItem> getCart() {
        return cart;
    }
    
    public int getCartQuantity() {
    	int cartQuantity = 0;
    	for(CartItem cartItem: cart) {
    		cartQuantity += cartItem.getQuantity();
    	}
    	return cartQuantity;
    }
    
    public double getGrandTotal() {
    	double grandTotal = cart.stream().mapToDouble(item -> (Double) item.getTotal()).sum();
    	return grandTotal;
    }
    
    public void clearCart() {
    	cart.clear();
    }
}
