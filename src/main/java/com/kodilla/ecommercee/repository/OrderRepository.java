package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Override
    Optional<Order> findById(Long id);
    Optional<Order> findByTotalCost(BigDecimal totalCost);
    Optional<Order> findByCreationDate(LocalDate creationDate);
    @Override
    void deleteById(Long id);
}