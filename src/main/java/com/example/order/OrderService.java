package com.example.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StockValidator stockValidator;

    @Transactional
    public Long order(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        stockValidator.validate(product, quantity);
        product.decreaseStock(quantity);
        return orderRepository.save(Order.of(product, quantity)).getId();
    }
}
