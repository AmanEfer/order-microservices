package com.amanefer.orderservice.order.controller;

import com.amanefer.orderservice.order.model.dto.CreateOrderRequest;
import com.amanefer.orderservice.order.model.dto.OrderResponse;
import com.amanefer.orderservice.order.service.OrderService;
import com.amanefer.orderservice.user.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createNewOrder(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody CreateOrderRequest request
    ) {
        var response = orderService.createOrder(user.getId(), request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderById(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable("id") Long id
    ) {
        var response = orderService.deleteOrderById(user.getId(), id);

        return ResponseEntity.ok(response);
    }
}
