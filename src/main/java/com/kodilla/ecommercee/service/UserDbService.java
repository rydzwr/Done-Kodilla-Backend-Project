package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.controller.exceptions.BlockedUserException;
import com.kodilla.ecommercee.controller.exceptions.UserNotFoundException;
import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserDbService {

    private final Random generator = new Random();
    private final UserRepository userRepository;

    @Autowired
    public UserDbService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void checkKeyValidity(User user) {
        if(user.getKey() != null) {
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime creationTime = LocalDateTime.of(user.getKeyGenerationDate(), user.getKeyGenerationTime());
            if(ChronoUnit.HOURS.between(creationTime, currentTime) >= 1 || !user.isStatus()) {
                user.setKey(null);
                user.setKeyGenerationDate(null);
                user.setKeyGenerationTime(null);
                userRepository.save(user);
            }
        }
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        Cart cart = new Cart();
        user.setCart(cart);
        cart.setUser(user);
        user.setStatus(true);
        return userRepository.save(user);
    }

    public void blockUser(Long userId) {
        User blockedUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        blockedUser.setStatus(false);
        userRepository.save(blockedUser);
    }

    public void generateKey(Long userId, String login) {
        User updatedUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if(!updatedUser.isStatus()) throw new BlockedUserException();
        if(updatedUser.getLogin().equals(login)) {
            updatedUser.setKeyGenerationTime(LocalTime.now());
            updatedUser.setKeyGenerationDate(LocalDate.now());
            int key = generator.nextInt(100000000);
            List<Integer> keys = userRepository.findAll()
                                               .stream()
                                               .map(User::getKey)
                                               .collect(Collectors.toList());
            while(true) {
                if(keys.contains(key)) {
                    key = generator.nextInt(100000000);
                } else {
                    break;
                }
            }
            updatedUser.setKey(key);
            userRepository.save(updatedUser);
        }
    }

    public Optional<Integer> getKey(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        checkKeyValidity(user);
        return Optional.ofNullable(user.getKey());
    }

}
