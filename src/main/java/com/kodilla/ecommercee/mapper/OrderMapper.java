package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.controller.exceptions.CartNotFoundException;
import com.kodilla.ecommercee.controller.exceptions.UserNotFoundException;
import com.kodilla.ecommercee.domain.*;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMapper {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    public OrderMapper(UserRepository userRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    private String parseDateToString(LocalDate date) {
        int day = date.getDayOfMonth();
        String dateString = (day<10)? "0" + day: Integer.toString(day);
        dateString += "/";
        int month = date.getMonthValue();
        dateString += (month<10)? "0" + month: Integer.toString(month);
        dateString += "/";
        int year = date.getYear();
        dateString += year;
        return dateString;
    }

    public Order mapToOrder(OrderDto orderDto) throws CartNotFoundException {
        Order order = new Order();
        order.setTotalCost(orderDto.getTotalCost());
        order.setCreationDate(LocalDate.parse(orderDto.getCreationDate(), formatter));
        order.setOrderConfirmation(orderDto.isOrderConfirmation());
        order.setUser(userRepository.findById(orderDto.getUserId()).orElseThrow(UserNotFoundException::new));
        order.setCart(cartRepository.findById(orderDto.getCartId()).orElseThrow(CartNotFoundException::new));
        return order;
    }

    public OrderDto mapToOrderDto(Order order) {
        String date = parseDateToString(order.getCreationDate());
        return new OrderDto(order.getId(),
                order.getTotalCost(),
                date,
                order.isOrderConfirmation(),
                order.getUser().getId(),
                order.getCart().getId(),
                order.getProducts()
                        .stream()
                        .map(Product::getId)
                        .collect(Collectors.toList()));
    }

    public List<OrderDto> mapToOrderDtoList(List<Order> orders) {
        return orders.stream()
                .map(this::mapToOrderDto)
                .collect(Collectors.toList());
    }
}
