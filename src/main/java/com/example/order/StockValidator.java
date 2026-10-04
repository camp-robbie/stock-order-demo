package com.example.order;

import org.springframework.stereotype.Component;

@Component
public class StockValidator {

    public void validate(Product product, int quantity) {
        if (product.getStock() < quantity) {
            throw new InsufficientStockException(product.getId(), quantity);
        }
    }
}
