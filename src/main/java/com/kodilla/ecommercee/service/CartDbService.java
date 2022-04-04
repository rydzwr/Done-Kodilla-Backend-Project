package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.controller.exceptions.CartNotFoundException;
import com.kodilla.ecommercee.controller.exceptions.ProductNotFoundException;
import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartDbService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public Cart getCart(final Long cartId) throws CartNotFoundException {
        return cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
    }

    public List<Cart> getCarts(final List<Long> cartIds) {
        return cartRepository.findAllById(cartIds);
    }

    public Cart saveCart(final Cart cart) {
        return cartRepository.save(cart);
    }

    public void deleteCart(final Long cartId) throws CartNotFoundException {
        try {
            cartRepository.deleteById(cartId);
        } catch (Exception e) {
            throw new CartNotFoundException();
        }
    }

    public Optional getUserByLogin(final String login) {
        return userRepository.findByLogin(login);
    }

    public Optional getUserByPhoneNumber(final Integer phoneNr) {
        return userRepository.findByPhoneNumber(phoneNr);
    }

    public Optional getOrderById(final Long id) {
        return orderRepository.findById(id);
    }

    public Optional getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void getProductById(long id) {
        productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Cart> getCarts() {
        return cartRepository.findAll();
    }

    public Cart addToCart(final Long idCart, final Long idProduct) throws CartNotFoundException, ProductNotFoundException, ProductNotFoundException {
        Cart cart = cartRepository.findById(idCart).orElseThrow(CartNotFoundException::new);
        Product product = productRepository.findById(idProduct).orElseThrow(ProductNotFoundException::new);

        if (product.getAvailability()) {
            cart.getProductInTheCart().add(product);
            cartRepository.save(cart);
            return cart;

        } else throw new ProductNotFoundException();
    }

    public Cart deleteFromCart(final Long idCart, final Long idProduct) throws CartNotFoundException, ProductNotFoundException {
        Cart cart = cartRepository.findById(idCart).orElseThrow(CartNotFoundException::new);

        Product product = cart.getProductInTheCart().stream()
                .filter(p->p.getId().equals(idProduct))
                .findFirst().orElseThrow(ProductNotFoundException::new);

        cart.getProductInTheCart().remove(product);
        cartRepository.save(cart);
        return cart;
    }

}
