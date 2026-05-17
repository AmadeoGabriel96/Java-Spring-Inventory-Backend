package com.MoyanoManchon.FinalProject.dto;

public class InvoiceDetailsDTO {
    private Long id;
    private int amount;
    private double price;
    private ProductDTO product;

    public InvoiceDetailsDTO() {
    }

    public InvoiceDetailsDTO(Long id, int amount, double price, ProductDTO product) {
        this.id = id;
        this.amount = amount;
        this.price = price;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }
}
