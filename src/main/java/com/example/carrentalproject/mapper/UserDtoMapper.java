package com.example.carrentalproject.mapper;

import com.example.carrentalproject.domain.User;
import com.example.carrentalproject.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDtoMapper {

        public static List<UserDto> mapUserToUserDto(List<User> users) {
                return users.stream()
                        .map(user -> new UserDto(user.getId(), user.getFirstName(),
                                user.getLastName(), user.getUsername(), user.getPassword(),
                                user.getEmail(), user.getPhone())).collect(Collectors.toList());
        }

}
