package com.example.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> order(@Valid @RequestBody OrderRequest request) {
        Long orderId = orderService.order(request.productId(), request.quantity());
        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderResponse(orderId));
    }

    public record OrderRequest(
            @NotNull Long productId,
            @Min(1) int quantity
    ) {
    }

    public record OrderResponse(Long orderId) {
    }
}
