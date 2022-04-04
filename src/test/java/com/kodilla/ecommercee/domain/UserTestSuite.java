package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;


@SpringBootTest
public class UserTestSuite {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    OrderRepository orderRepository;

    User user = new User();
    Cart cart = new Cart();

    @BeforeEach
    public void prepareTests() {
        user.setLogin("test");
        user.setPhoneNumber(629714902);
        user.setEmail("test@gmail.com");
        user.setAddress("test address");
        user.setStatus(true);
        user.setCart(cart);
        cart.setUser(user);
    }

    @Test
    public void findUserByAllFieldsTest() {
        //Given
        //When
        userRepository.save(user);
        //Then
        Assertions.assertTrue(userRepository.findById(user.getId()).isPresent());
        Assertions.assertTrue(userRepository.findByAddress(user.getAddress()).isPresent());
        Assertions.assertTrue(userRepository.findByEmail(user.getEmail()).isPresent());
        Assertions.assertTrue(userRepository.findByLogin(user.getLogin()).isPresent());
        Assertions.assertTrue(userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent());
        Assertions.assertTrue(userRepository.findByCart(user.getCart()).isPresent());
        //CleanUp
        userRepository.deleteById(user.getId());
    }

    @Test
    public void findUsersTest() {
        //Given
        User secondUser = new User();
        secondUser.setLogin("test");
        secondUser.setPhoneNumber(629714902);
        secondUser.setEmail("test@gmail.com");
        secondUser.setAddress("test address");
        //When
        userRepository.save(user);
        userRepository.save(secondUser);
        //Then
        Assertions.assertEquals(2, userRepository.findAll().size());
        //CleanUp
        userRepository.deleteById(user.getId());
        userRepository.deleteById(secondUser.getId());
    }

    @Test
    public void checkIfCartIsSavedTest() {
        //Given
        //When
        userRepository.save(user);
        //Then
        Assertions.assertTrue(cartRepository.findById(cart.getId()).isPresent());
        //CleanUp
        userRepository.deleteById(user.getId());
    }

    @Test
    public void checkIfCartIsRemovedTest() {
        //Given
        //When
        userRepository.save(user);
        userRepository.deleteById(user.getId());
        //Then
        Assertions.assertFalse(cartRepository.findById(cart.getId()).isPresent());
    }

    @Test
    public void checkIfOrdersAreRemoved() {
        //Given
        Order order = new Order();
        order.setTotalCost(new BigDecimal(100));
        order.setCreationDate(LocalDate.now());
        order.setOrderConfirmation(true);
        user.getOrders().add(order);
        order.setUser(user);
        //When
        userRepository.save(user);
        orderRepository.save(order);
        userRepository.deleteById(user.getId());
        //Then
        Assertions.assertFalse(orderRepository.findById(order.getId()).isPresent());
    }
}
