package com.nichoscode.sportyshoes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.nichoscode.sportyshoes.service.CartService;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private CartService cartService;

    @ModelAttribute("cartQuantity")
    public int getCartQuantity() {
        return cartService.getCartQuantity();
    }
}

