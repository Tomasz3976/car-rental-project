package com.example1.carrental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserEditDto {

        private Long id;
        private String firstName;
        private String lastName;
        private String username;
        private String password;
        private String email;
        private Integer phone;

}
