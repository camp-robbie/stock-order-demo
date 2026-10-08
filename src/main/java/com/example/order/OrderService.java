package com.example.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Long order(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        if (product.getStock() < quantity) {
            throw new InsufficientStockException(product.getId(), quantity);
        }
        product.decreaseStock(quantity);
        return orderRepository.save(Order.of(product, quantity)).getId();
    }
}
