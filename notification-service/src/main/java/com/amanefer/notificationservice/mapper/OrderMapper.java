package com.amanefer.notificationservice.mapper;

import com.amanefer.notificationservice.kafka.message.OrderMessage;
import com.amanefer.notificationservice.model.dto.OrderPageResponse;
import com.amanefer.notificationservice.model.dto.OrderResponse;
import com.amanefer.notificationservice.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = OrderItemMapper.class
)
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Order toEntity(OrderMessage message);

    @Mapping(source = "id", target = "orderId")
    OrderResponse toOrderResponse(Order order);

    List<OrderResponse> toOrderResponseList(List<Order> orders);

    @Mapping(source = "content", target = "orders")
    OrderPageResponse toOrderPageResponse(Page<Order> page);
}
