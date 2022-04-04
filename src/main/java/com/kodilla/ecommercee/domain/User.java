package com.kodilla.ecommercee.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "USERS")
public class User {

    @Id
    @NotNull
    @GeneratedValue
    @Column(name = "USER_ID", unique = true)
    private Long id;
    @Column(name = "LOGIN")
    private String login;
    @Column(name = "PHONE_NUMBER")
    private Integer phoneNumber;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "GENERATED_KEY")
    private Integer key;
    @Column(name = "KEY_GENERATION_DATE")
    private LocalDate keyGenerationDate;
    @Column(name = "KEY_GENERATION_TIME")
    private LocalTime keyGenerationTime;
    @Column(name = "USER_STATUS")
    private boolean status;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "CART_ID")
    private Cart cart;
    
    @OneToMany (targetEntity = Order.class,
                mappedBy = "user",
                cascade = CascadeType.REMOVE,
                fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    public User(String login) {
        this.login = login;
    }

}
