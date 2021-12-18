package com.example1.carrental.mapper;

import com.example1.carrental.domain.User;
import com.example1.carrental.dto.UserDisplayDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDisplayDtoMapper {

        public static List<UserDisplayDto> mapUserToUserDisplayDto(List<User> users) {
                return users.stream()
                        .map(user -> new UserDisplayDto(user.getId(), user.getFirstName(),
                                user.getLastName(), user.getUsername(), user.getPassword(),
                                user.getEmail(), user.getPhone())).collect(Collectors.toList());
        }

}
