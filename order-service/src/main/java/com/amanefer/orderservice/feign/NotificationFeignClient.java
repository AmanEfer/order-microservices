package com.amanefer.orderservice.feign;

import com.amanefer.orderservice.model.dto.order.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "notification-service",
        url = "${app.feign.notification-url}",
        path = "/api/v1/analytics/orders"
)
public interface NotificationFeignClient {

    @GetMapping("/my")
    List<OrderResponse> getUserOrders(@RequestParam("userId") Long userId);

    @DeleteMapping("/my")
    String deleteUserOrderById(@RequestParam("userId") Long userId, @RequestParam("orderId") Long orderId
    );
}
