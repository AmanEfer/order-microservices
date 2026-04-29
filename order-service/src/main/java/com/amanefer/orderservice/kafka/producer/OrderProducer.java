package com.amanefer.orderservice.kafka.producer;

import com.amanefer.orderservice.kafka.message.OrderMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProducer {

    @Value("${app.kafka.topics.orders}")
    private String topic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(OrderMessage event) {
        kafkaTemplate.send(topic, event);
    }
}
