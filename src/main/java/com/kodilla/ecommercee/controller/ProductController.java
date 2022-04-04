package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.controller.exceptions.GroupNotFoundException;
import com.kodilla.ecommercee.controller.exceptions.ProductNotFoundException;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.service.ProductDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductDbService productDbService;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<Product> products = productDbService.getAllProducts();
         return ResponseEntity.ok( productMapper.mapToProductDtoList(products));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createProduct(@RequestBody ProductDto productDto) throws GroupNotFoundException {
        Product product = productMapper.mapToProduct(productDto);
         productDbService.saveProduct(product);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long productId)
        throws ProductNotFoundException {
            return ResponseEntity.ok(productMapper.mapToProductDto(productDbService.getProduct(productId)));
    }

    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) throws GroupNotFoundException {
             Product product = productMapper.mapToProduct(productDto);
             Product savedProduct = productDbService.saveProduct(product);
             return ResponseEntity.ok(productMapper.mapToProductDto(savedProduct));
        }

    @DeleteMapping(value = "/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId)
        throws ProductNotFoundException {
        productDbService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

}
