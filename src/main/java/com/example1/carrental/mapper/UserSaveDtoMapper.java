package com.example1.carrental.mapper;

import com.example1.carrental.domain.User;
import com.example1.carrental.dto.UserSaveDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserSaveDtoMapper {

        public static User mapToUser(UserSaveDto userSaveDto) {

                return User.builder()
                        .firstName(userSaveDto.getFirstName())
                        .lastName(userSaveDto.getLastName())
                        .username(userSaveDto.getUsername())
                        .password(userSaveDto.getPassword())
                        .email(userSaveDto.getEmail())
                        .phone(userSaveDto.getPhone())
                        .roles(new ArrayList<>())
                        .build();

        }

}
