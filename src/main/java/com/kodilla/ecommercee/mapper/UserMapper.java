package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.UsersDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public User mapToUser(UsersDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setLogin(userDto.getLogin());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        return user;
    }

    public UsersDto mapToUserDto(User user) {
        return new UsersDto(user.getId(),
                            user.getLogin(),
                            user.getPhoneNumber(),
                            user.getEmail(),
                            user.getAddress(),
                            user.getCart().getId());
    }

    public List<UsersDto> mapToUserDtoList(List<User> users) {
        return users.stream()
                    .map(this::mapToUserDto)
                    .collect(Collectors.toList());
    }

}
