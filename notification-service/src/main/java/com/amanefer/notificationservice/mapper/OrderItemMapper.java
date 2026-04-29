package com.amanefer.notificationservice.mapper;

import com.amanefer.notificationservice.kafka.message.OrderItemMessage;
import com.amanefer.notificationservice.model.dto.OrderItemPageResponse;
import com.amanefer.notificationservice.model.dto.OrderItemResponse;
import com.amanefer.notificationservice.model.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderItemMapper {

    @Mapping(target = "id", ignore = true)
    OrderItem toEntity(OrderItemMessage message);

    List<OrderItem> toEntityList(List<OrderItemMessage> messages);

    OrderItemResponse toOrderItemResponse(OrderItem item);

    List<OrderItemResponse> toOrderItemResponseList(List<OrderItem> items);

    @Mapping(source = "content", target = "items")
    OrderItemPageResponse toOrderItemPageResponse(Page<OrderItem> items);
}
