package com.example1.carrental.mapper;

import com.example1.carrental.domain.CreditCard;
import com.example1.carrental.domain.User;
import com.example1.carrental.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDtoMapper {

        public static final CreditCard CREDIT_CARD = null;

        public static User mapToUser(UserDto userDto) {

                return User.builder()
                        .firstName(userDto.getFirstName())
                        .lastName(userDto.getLastName())
                        .username(userDto.getUsername())
                        .password(userDto.getPassword())
                        .email(userDto.getEmail())
                        .phone(userDto.getPhone())
                        .creditCard(CREDIT_CARD)
                        .roles(new ArrayList<>())
                        .build();

        }

}
