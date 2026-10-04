package com.example.order;

import lombok.Getter;

@Getter
public class InsufficientStockException extends RuntimeException {

    private final Long productId;
    private final int quantity;

    public InsufficientStockException(Long productId, int quantity) {
        super("재고가 부족합니다. productId=" + productId + ", quantity=" + quantity);
        this.productId = productId;
        this.quantity = quantity;
    }
}
