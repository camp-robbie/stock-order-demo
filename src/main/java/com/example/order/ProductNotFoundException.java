package com.example.order;

import lombok.Getter;

@Getter
public class ProductNotFoundException extends RuntimeException {

    private final Long productId;

    public ProductNotFoundException(Long productId) {
        super("상품을 찾을 수 없습니다. productId=" + productId);
        this.productId = productId;
    }
}
