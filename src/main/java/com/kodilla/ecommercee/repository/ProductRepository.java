package com.kodilla.ecommercee.repository;

import com.fasterxml.jackson.databind.introspect.AnnotationCollector;
import com.kodilla.ecommercee.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    List<Product> findAll();

    @Override
    Optional<Product> findById(Long id);

    @Override
    void deleteById(Long id);

    List<Product> findByAvailability(boolean availability);

    Optional<Product> findByName(String name);

    Optional<Product> findByPrice(BigDecimal price);

    Optional<Product> findByDescription(String description);
}
