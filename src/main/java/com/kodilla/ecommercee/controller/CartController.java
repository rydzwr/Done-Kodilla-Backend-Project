package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.controller.exceptions.CartNotFoundException;
import com.kodilla.ecommercee.controller.exceptions.OrderNotFoundException;
import com.kodilla.ecommercee.controller.exceptions.ProductNotFoundException;
import com.kodilla.ecommercee.controller.exceptions.UserNotFoundException;
import com.kodilla.ecommercee.domain.CartDto;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.mapper.CartMapper;
import com.kodilla.ecommercee.mapper.OrderMapper;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.service.CartDbService;
import com.kodilla.ecommercee.service.OrderDbService;
import com.kodilla.ecommercee.service.ProductDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartDbService cartDbService;
    private final CartMapper cartMapper;
    private final ProductDbService productDbService;
    private final ProductMapper productMapper;
    private final OrderDbService orderDbService;

    @GetMapping("/{idCart}")
    public ResponseEntity<List<ProductDto>> getProductsFromCart(@PathVariable Long idCart) throws CartNotFoundException {
        List<Product> products = productDbService.getAllProducts();
        cartDbService.getCart(idCart).setProductInTheCart(products);
        return ResponseEntity.ok(productMapper.mapToProductDtoList(products));
    }


    @PutMapping("addProduct/{idCart}/{idProduct}")
    public ResponseEntity<CartDto> addProductToCart(@PathVariable Long idCart, @PathVariable Long idProduct) throws ProductNotFoundException, CartNotFoundException {
        return ResponseEntity.ok(cartMapper.mapToCartDto(cartDbService.addToCart(idCart, idProduct)));
    }

    @DeleteMapping("/{idCart}/{idProduct}")
    public ResponseEntity<CartDto> deleteProductFromCart(@PathVariable Long idCart, @PathVariable Long idProduct) throws CartNotFoundException, ProductNotFoundException {
        return ResponseEntity.ok(cartMapper.mapToCartDto(cartDbService.deleteFromCart(idCart, idProduct)));
    }


    @PostMapping("/o/{idCart}")
    public ResponseEntity<Void> createOrderFromCart(@PathVariable Long idCart) throws OrderNotFoundException, CartNotFoundException {
        Order newOrder = cartDbService.getCart(idCart).getOrder();
        orderDbService.getOrder(newOrder.getId());
        return ResponseEntity.ok().build();
    }


    @PostMapping("/u/{userId}")
    public ResponseEntity<Void> createEmptyCart(@PathVariable Long userId) throws UserNotFoundException {
        cartDbService.saveCart(cartMapper.mapToEmptyCart(userId));
        return ResponseEntity.ok().build();
    }

}


