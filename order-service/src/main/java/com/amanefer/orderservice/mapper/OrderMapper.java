package com.amanefer.orderservice.mapper;

import com.amanefer.orderservice.order.model.dto.OrderItemResponse;
import com.amanefer.orderservice.order.model.dto.OrderResponse;
import com.amanefer.orderservice.order.model.entity.Order;
import com.amanefer.orderservice.order.model.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    @Mapping(source = "id", target = "orderId")
    OrderResponse toOrderResponse(Order order);

    OrderItemResponse toOrderItemResponse(OrderItem orderItem);

    List<OrderResponse> toOrderResponseList(List<Order> users);
}
