package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.controller.exceptions.CartNotFoundException;
import com.kodilla.ecommercee.controller.exceptions.OrderNotFoundException;
import com.kodilla.ecommercee.controller.exceptions.ProductNotFoundException;
import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderDbService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    public void saveOrder(Order order) throws CartNotFoundException {
        Cart cart = cartRepository.findById(order.getCart().getId()).orElseThrow(CartNotFoundException::new);
        cart.setOrder(order);
        orderRepository.save(order);
    }

    public void deleteOrder(Long orderId) throws CartNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        Cart cart = cartRepository.findById(order.getCart().getId()).orElseThrow(CartNotFoundException::new);
        cart.setOrder(null);
        try {
            orderRepository.deleteById(orderId);
            cartRepository.save(cart);
        } catch (Exception e) {
            throw new OrderNotFoundException();
        }
    }

    public void addProductToOrder(Long orderId, Long productId) throws ProductNotFoundException{
        Order updatedOrder = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        updatedOrder.getProducts().add(product);
        orderRepository.save(updatedOrder);
    }

    public void removeProductFromOrder(Long orderId, Long productId) throws ProductNotFoundException {
        Order updatedOrder = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        Product removedProduct = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        updatedOrder.getProducts().remove(removedProduct);
        orderRepository.save(updatedOrder);
    }
}
