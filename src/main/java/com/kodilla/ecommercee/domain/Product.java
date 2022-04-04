package com.kodilla.ecommercee.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "PRODUCT_ID", unique = true)
    private Long id;
    private String name;
    private BigDecimal price;
    private Boolean availability;
    private String description;

    public Product(Long id, String name, BigDecimal price, Boolean availability, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availability = availability;
        this.description = description;
    }

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "JOIN_PRODUCTS_CARTS",
            joinColumns = {@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "CART_ID", referencedColumnName = "CART_ID")}
    )
    private List<Cart> carts = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "JOIN_PRODUCTS_ORDERS",
            joinColumns = {@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDER_ID")}
    )
    private List<Order> orders = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    public Product(String name, BigDecimal price, Group group) {
        this.name = name;
        this.price = price;
        this.group = group;
    }

    public Product(Long id, String name, BigDecimal price, Boolean availability, String description, List<Cart> carts, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availability = availability;
        this.description = description;
        this.carts = carts;
        this.orders = orders;
    }

}
