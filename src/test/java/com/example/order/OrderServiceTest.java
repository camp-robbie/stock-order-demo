package com.example.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("요구사항 4,5 - 재고가 충분하면 주문에 성공하고 재고가 차감된다")
    void 재고가_충분하면_주문에_성공한다() {
        Product product = productRepository.save(new Product("키보드", 10));

        Long orderId = orderService.order(product.getId(), 3);

        assertThat(orderId).isNotNull();
        assertThat(productRepository.findById(product.getId()).get().getStock()).isEqualTo(7);
        assertThat(orderRepository.count()).isEqualTo(1);
    }

    @Test
    void 재고와_주문수량이_같으면_주문에_성공한다() {
        Product product = productRepository.save(new Product("키보드", 10));

        orderService.order(product.getId(), 10);

        assertThat(productRepository.findById(product.getId()).get().getStock()).isZero();
        assertThat(orderRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("요구사항 6 - 재고가 부족하면 주문에 실패하고 재고와 주문 내역이 변하지 않는다")
    void 재고가_부족하면_주문에_실패한다() {
        Product product = productRepository.save(new Product("키보드", 9));

        assertThatThrownBy(() -> orderService.order(product.getId(), 10))
                .isInstanceOf(InsufficientStockException.class);

        assertThat(productRepository.findById(product.getId()).get().getStock()).isEqualTo(9);
        assertThat(orderRepository.count()).isZero();
    }
}
