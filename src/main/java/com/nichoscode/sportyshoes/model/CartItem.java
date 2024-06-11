package com.nichoscode.sportyshoes.model;

public class CartItem {
    private Product product;
    private int quantity;
   
    // Constructors, getters, and setters
    public CartItem() {}

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

	public double getTotal() {
		return quantity * product.getPrice();
	}
    
    
}

