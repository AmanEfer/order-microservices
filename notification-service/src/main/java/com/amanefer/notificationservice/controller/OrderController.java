package com.amanefer.notificationservice.controller;

import com.amanefer.notificationservice.model.dto.OrderItemPageResponse;
import com.amanefer.notificationservice.model.dto.OrderItemResponse;
import com.amanefer.notificationservice.model.dto.OrderPageResponse;
import com.amanefer.notificationservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<OrderPageResponse> getAllOrders(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "3") Integer size
    ) {
        var response = orderService.getAllOrders(PageRequest.of(page, size));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{order_id}")
    public ResponseEntity<List<OrderItemResponse>> getAllPurchasesByOrderId(
            @PathVariable("order_id") Long orderId
    ) {
        var response = orderService.getAllOrderItemsByOrderId(orderId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<OrderItemPageResponse> getAllPurchasesByUserId(
            @PathVariable("user_id") Long userId,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "3") Integer size
    ) {
        var response = orderService.getAllOrderItemsByUserId(userId, PageRequest.of(page, size));

        return ResponseEntity.ok(response);
    }
}
