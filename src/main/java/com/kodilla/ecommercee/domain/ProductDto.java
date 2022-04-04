package com.kodilla.ecommercee.domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private Boolean availability;
    private String description;
    private List<Long> cartsId;
    private List<Long> ordersId;
    private Long groupId;
}
