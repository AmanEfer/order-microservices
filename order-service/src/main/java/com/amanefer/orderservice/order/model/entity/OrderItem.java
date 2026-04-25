package com.amanefer.orderservice.order.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_item_seq"
    )
    @SequenceGenerator(
            name = "order_item_seq",
            sequenceName = "order_item_sequence",
            allocationSize = 50,
            initialValue = 1
    )
    private Long id;

    private Long productId;

    private String name;

    private Integer quantity;

    private BigDecimal price;

    private Integer sale;

    private BigDecimal priceWithSale;

    private BigDecimal totalPrice;

}
