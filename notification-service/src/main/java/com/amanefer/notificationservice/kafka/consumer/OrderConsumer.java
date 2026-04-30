package com.amanefer.notificationservice.kafka.consumer;

import com.amanefer.notificationservice.kafka.message.OrderMessage;
import com.amanefer.notificationservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderConsumer {

    private final OrderService orderService;

    @KafkaListener(
            topics = "${app.kafka.topics.orders}",
            groupId = "${app.kafka.group-id}"
    )
    public void consumeEvent(OrderMessage event) {
        orderService.createNewOrder(event);
    }
}
