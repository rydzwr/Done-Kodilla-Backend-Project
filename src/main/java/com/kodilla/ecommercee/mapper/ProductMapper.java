package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.controller.exceptions.GroupNotFoundException;
import com.kodilla.ecommercee.service.ProductDbService;
import com.kodilla.ecommercee.service.ProductDbService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductMapper {

    private ProductDbService productDbService;

    public ProductMapper(ProductDbService productDbService) {
        this.productDbService = productDbService;
    }

    public Product mapToProduct(final ProductDto productDto) throws GroupNotFoundException {
        return new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getPrice(),
                productDto.getAvailability(),
                productDto.getDescription(),
                productDbService.getCarts(productDto.getCartsId()),
                productDbService.getOrders(productDto.getOrdersId()),
                productDbService.getGroup(productDto.getGroupId())
        );
    }

    public ProductDto mapToProductDto(final Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getAvailability(),
                product.getDescription(),
                product.getCarts().stream().map(cart -> cart.getId()).collect(Collectors.toList()),
                product.getOrders().stream().map(order -> order.getId()).collect(Collectors.toList()),
                product.getGroup().getId());
    }

    public List<ProductDto> mapToProductDtoList(final List<Product> productList) {
        return productList.stream()
                .map(this::mapToProductDto)
                .collect(Collectors.toList());
    }

}
