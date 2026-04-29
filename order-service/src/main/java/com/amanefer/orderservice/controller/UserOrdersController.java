package com.amanefer.orderservice.controller;

import com.amanefer.orderservice.kafka.message.OrderMessage;
import com.amanefer.orderservice.model.dto.order.CreateOrderRequest;
import com.amanefer.orderservice.model.dto.order.OrderResponse;
import com.amanefer.orderservice.service.UserOrdersService;
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
@RequestMapping("/api/v1/user/orders")
@RequiredArgsConstructor
public class UserOrdersController {

    private final UserOrdersService userOrdersService;

    @PostMapping
    public ResponseEntity<OrderMessage> createNewOrder(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @Valid @RequestBody CreateOrderRequest request
    ) {
        var response = userOrdersService.createOrder(userId, request);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/my")
    public ResponseEntity<List<OrderResponse>> getUserOrders(
            @AuthenticationPrincipal(expression = "id") Long userId
    ) {
        var orders = userOrdersService.getUserOrders(userId);

        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("/my/{id}")
    public ResponseEntity<String> deleteOrderById(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @PathVariable("id") Long orderId
    ) {
        String response = userOrdersService.deleteUserOrderById(userId, orderId);

        return ResponseEntity.ok(response);
    }

}
