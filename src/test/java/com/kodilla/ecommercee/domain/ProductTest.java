package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.GroupRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    GroupRepository groupRepository;

    public  Product product = new Product();

    @BeforeEach
    public  void prepareTests() {
        product.setName("test");
        product.setPrice(new BigDecimal(23.50));
        product.setAvailability(true);
        product.setDescription("test address");
    }

    @Test
    public void findProductByAllFieldsTest() {
        //Given
        //When
        productRepository.save(product);
        //Then
        assertTrue(productRepository.findById(product.getId()).isPresent());
        assertTrue(productRepository.findByName(product.getName()).isPresent());
        assertTrue(productRepository.findByPrice(product.getPrice()).isPresent());
        assertFalse(productRepository.findByAvailability(product.getAvailability()).isEmpty());
        assertTrue(productRepository.findByDescription(product.getDescription()).isPresent());
        //CleanUp
        productRepository.deleteById(product.getId());
    }

    @Test
    public void saveProductTest() {
        //Given
        //When
        productRepository.save(product);
        //Then
        assertTrue(productRepository.findById(product.getId()).isPresent());
        //CleanUp
        productRepository.deleteById(product.getId());
    }
    @Test
    public void deleteProductTest() {
        //Given
        //When
        productRepository.save(product);
        productRepository.findById(product.getId()).isPresent();
        productRepository.deleteById(product.getId());
        //Then
        assertFalse(productRepository.findById(product.getId()).isPresent());
        //CleanUp
    }
    @Test
    public void updateProductTest() {
        //Given
        productRepository.save(product);
        //When
        product.setDescription("new");
        productRepository.save(product);
        product = productRepository.findById(product.getId()).get();
        assertEquals("new",product.getDescription());

        //CleanUp
        productRepository.deleteById(product.getId());
    }

    @Test
    public void checkIfCartIsRemovedTest() {
        List<Cart> carts = new ArrayList<>();
        Cart cart = new Cart();
        carts.add(cart);
        product.setCarts(carts);
        productRepository.save(product);

        //CleanUp
        productRepository.deleteById(product.getId());
        assertFalse(productRepository.findById(product.getId()).isPresent());
        assertTrue(cartRepository.findById(cart.getId()).isPresent());
    }

    @Test
    public void checkIfOrderIsRemovedTest() {
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setOrderConfirmation(true);
        order.setCreationDate(LocalDate.now());
        order.setTotalCost(new BigDecimal(23.50));
        orders.add(order);
        product.setOrders(orders);
        productRepository.save(product);

        //CleanUp
        productRepository.deleteById(product.getId());
        assertFalse(productRepository.findById(product.getId()).isPresent());
        assertTrue(orderRepository.findById(order.getId()).isPresent());
    }

    @Test
    public void checkIfGroupIsRemovedTest() {
        Group group = new Group();
        product.setGroup(group);
        groupRepository.save(group);
        productRepository.save(product);

        //CleanUp
        productRepository.deleteById(product.getId());
        assertFalse(productRepository.findById(product.getId()).isPresent());
        assertTrue(groupRepository.findById(group.getId()).isPresent());
    }

}

