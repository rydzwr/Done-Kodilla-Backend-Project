package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.controller.exceptions.CartNotFoundException;
import com.kodilla.ecommercee.controller.exceptions.UserNotFoundException;
import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.CartDto;
import com.kodilla.ecommercee.service.CartDbService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartMapper {

    private CartDbService cartDbService;

    public CartMapper(CartDbService cartDbService) {
        this.cartDbService = cartDbService;
    }

    public Cart mapToCart(final CartDto cartDto) throws CartNotFoundException {
        Cart cart = new Cart();
        cart.setId(cartDto.getId());
        cart.setUser(cartDto.getUser());
        cart.setOrder(cartDto.getOrder());
        return cart;
    }

    public CartDto mapToCartDto(final Cart cart) {

        return new CartDto(
                cart.getId(),
                cart.getUser(),
                cart.getOrder(),
                cart.getProductInTheCart());
    }

    public Cart mapToEmptyCart(final Long userId) throws UserNotFoundException {
        Cart cart = new Cart();
        return cart;
    }

    public List<CartDto> mapToCartDtoList(final List<Cart> cartList) {
        return cartList.stream().map(this::mapToCartDto).collect(Collectors.toList());
    }

}
