package com.tecsup.minishop.dto;

import com.tecsup.minishop.model.Product;

public record ProductRequest(String name, Double price, Integer stock) {

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .price(price)
                .stock(stock)
                .build();
    }
}