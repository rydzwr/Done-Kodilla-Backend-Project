package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.controller.exceptions.InvalidKeyException;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.UsersDto;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.service.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/users")
public class UsersController {

    private final UserMapper userMapper;
    private final UserDbService userDbService;

    @Autowired
    public UsersController(UserMapper userMapper, UserDbService userDbService) {
        this.userMapper = userMapper;
        this.userDbService = userDbService;
    }

    @GetMapping
    public ResponseEntity<List<UsersDto>> getUsers() {
        List<UsersDto> usersDto = userMapper.mapToUserDtoList(userDbService.getUsers());
        return ResponseEntity.ok(usersDto);
    }

    @GetMapping(value = "{usersId}")
    public ResponseEntity<UsersDto> getUser(@PathVariable Long usersId) {
        UsersDto usersDto = userMapper.mapToUserDto(userDbService.getUser(usersId));
        return ResponseEntity.ok(usersDto);
    }

    @PostMapping
    public ResponseEntity<UsersDto> addUsers(@RequestBody UsersDto usersDto) {
        User user = userMapper.mapToUser(usersDto);
        return ResponseEntity.ok(userMapper.mapToUserDto(userDbService.createUser(user)));
    }

    @PutMapping(value = "block_user/{userId}")
    public ResponseEntity<Void> blockUser(@PathVariable Long userId) {
        userDbService.blockUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "key/{usersId}")
    public ResponseEntity<Integer> fetchKey(@PathVariable Long usersId) {
        Integer key = userDbService.getKey(usersId).orElseThrow(InvalidKeyException::new);
        return ResponseEntity.ok(key);
    }

    @PutMapping(value = "generate_key/{usersId}/{login}")
    public ResponseEntity<Void> generateKey(@PathVariable Long usersId, @PathVariable String login) {
        userDbService.generateKey(usersId, login);
        return ResponseEntity.ok().build();
    }
}
