package com.example1.carrental.mapper;

import com.example1.carrental.domain.CreditCard;
import com.example1.carrental.domain.User;
import com.example1.carrental.dto.UserSaveDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserSaveDtoMapper {

        public static final CreditCard CREDIT_CARD = null;

        public static User mapToUser(UserSaveDto userSaveDto) {

                return User.builder()
                        .firstName(userSaveDto.getFirstName())
                        .lastName(userSaveDto.getLastName())
                        .username(userSaveDto.getUsername())
                        .password(userSaveDto.getPassword())
                        .email(userSaveDto.getEmail())
                        .phone(userSaveDto.getPhone())
                        .creditCard(CREDIT_CARD)
                        .roles(new ArrayList<>())
                        .build();

        }

}
