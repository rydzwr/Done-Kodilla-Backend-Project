package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTestSuite {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    private final Order order = new Order();

    @BeforeEach
    public void prepareTests() {
        order.setTotalCost(new BigDecimal(100));
        order.setCreationDate(LocalDate.of(1990, 12, 15));
        order.setOrderConfirmation(true);
    }

    @Test
    public void findOrderByAllFieldsTest() {
        //Given
        //When
        orderRepository.save(order);
        //Then
        Assertions.assertTrue(orderRepository.findById(order.getId()).isPresent());
        Assertions.assertTrue(orderRepository.findByTotalCost(order.getTotalCost()).isPresent());
        Assertions.assertTrue(orderRepository.findByCreationDate(order.getCreationDate()).isPresent());
        //CleanUp
        orderRepository.deleteById(order.getId());
    }

    @Test
    public void saveOrderTest() {
        //Given
        //When
        orderRepository.save(order);
        //Then
        assertTrue(orderRepository.findById(order.getId()).isPresent());
        //CleanUp
        orderRepository.deleteById(order.getId());
    }

    @Test
    public void deleteOrderTest() {
        //Given
        //When
        orderRepository.save(order);
        orderRepository.deleteById(order.getId());
        //Then
        Assertions.assertFalse(orderRepository.findById(order.getId()).isPresent());
    }

    @Test
    public void checkIfUserIsNotRemovedTest() {
        //Given
        User user = new User();
        user.getOrders().add(order);
        order.setUser(user);
        //When
        userRepository.save(user);
        orderRepository.save(order);
        orderRepository.deleteById(order.getId());
        //Then
        Assertions.assertTrue(userRepository.findById(user.getId()).isPresent());
        //CleanUp
        userRepository.deleteById(user.getId());
    }

    @Test
    public void checkIfCartIsSavedTest() {
        //Given
        Cart cart = new Cart();
        cart.setOrder(order);
        order.setCart(cart);
        //When
        orderRepository.save(order);
        //Then
        Assertions.assertTrue(cartRepository.findById(cart.getId()).isPresent());
        //CleanUp
        cartRepository.deleteById(cart.getId());
    }

    @Test
    public void checkIfOrderIsRemoved() {
        //Given
        Cart cart = new Cart();
        cart.setOrder(order);
        order.setCart(cart);
        //When
        orderRepository.save(order);
        cartRepository.deleteById(cart.getId());
        //Then
        Assertions.assertFalse(cartRepository.findById(cart.getId()).isPresent());
    }

    @Test
    public void checkIfProductIsSavedTest() {
        //Given
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setName("test");
        product.setPrice(new BigDecimal("23.5"));
        product.setAvailability(true);
        product.setDescription("test address");
        products.add(product);
        product.setOrders(Stream.of(order).collect(Collectors.toList()));
        order.setProducts(products);
        //When
        orderRepository.save(order);
        Assertions.assertEquals(1, productRepository.findAll().size());
        //CleanUp
        orderRepository.deleteById(order.getId());
    }

    @Test
    public void checkIfProductIsRemovedTest() {
        //Given
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setName("test");
        product.setPrice(new BigDecimal("23.50"));
        product.setAvailability(true);
        product.setDescription("test address");
        products.add(product);
        product.setOrders(Stream.of(order).collect(Collectors.toList()));
        order.setProducts(products);
        //When
        orderRepository.save(order);
        orderRepository.deleteById(order.getId());
        Assertions.assertEquals(0, productRepository.findAll().size());
    }

}