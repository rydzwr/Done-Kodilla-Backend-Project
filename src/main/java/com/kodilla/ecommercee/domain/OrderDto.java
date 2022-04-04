package com.kodilla.ecommercee.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private BigDecimal totalCost;
    private String creationDate;
    private boolean orderConfirmation;
    private Long userId;
    private Long cartId;
    private List<Long> productsId;
}
