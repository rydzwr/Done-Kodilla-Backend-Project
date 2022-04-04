package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    Optional<User> findById(Long id);
    @Override
    List<User> findAll();
    @Override
    void deleteById(Long id);

    Optional<User> findByLogin(String login);
    Optional<User> findByAddress(String address);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(int phoneNumber);
    Optional<User> findByCart(Cart cart);
}
