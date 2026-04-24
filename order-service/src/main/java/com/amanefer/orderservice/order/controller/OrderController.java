package com.amanefer.orderservice.order.controller;

import com.amanefer.orderservice.order.model.dto.CreateOrderRequest;
import com.amanefer.orderservice.order.model.dto.OrderResponse;
import com.amanefer.orderservice.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/my")
    public ResponseEntity<List<OrderResponse>> getUserOrders(
            @AuthenticationPrincipal(expression = "id") Long userId
    ) {
        List<OrderResponse> response = orderService.getUserOrders(userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createNewOrder(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @Valid @RequestBody CreateOrderRequest request
    ) {
        var response = orderService.createOrder(userId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderById(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @PathVariable("id") Long id
    ) {
        var response = orderService.deleteOrderById(userId, id);

        return ResponseEntity.ok(response);
    }
}
