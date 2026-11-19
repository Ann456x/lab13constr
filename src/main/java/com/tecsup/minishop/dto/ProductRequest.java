package com.tecsup.minishop.dto;

import com.tecsup.minishop.model.Product;

public class ProductRequest {

    private String name;
    private Double price;
    private Integer stock;

    public ProductRequest() {
    }

    public Product toEntity() {
        return Product.builder()
                .name(this.name)
                .price(this.price)
                .stock(this.stock)
                .build();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}