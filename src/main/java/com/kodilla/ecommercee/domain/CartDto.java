package com.kodilla.ecommercee.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CartDto {
    private Long id;
    private List<Long> productIds;
    private Long userId;

    private User user;
    private Order order;
    private List<Product> productId;

    public CartDto(Long id, User user, Order order, List<Product> productId) {
        this.id = id;
        this.user = user;
        this.order = order;
        this.productId = productId;
    }

}
