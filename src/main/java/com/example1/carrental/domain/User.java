package com.example1.carrental.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "User")
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", unique = true, nullable = false)
        private Long id;

        @Column(name = "firstName", nullable = false)
        private String firstName;

        @Column(name = "lastName", nullable = false)
        private String lastName;

        @Column(name = "username", nullable = false)
        private String username;

        @Column(name = "password", nullable = false)
        private String password;

        @Column(name = "email", nullable = false)
        private String email;

        @Column(name = "phone", nullable = false, length = 9)
        private Integer phone;

        @OneToOne(cascade = CascadeType.ALL)
        private CreditCard creditCard;

        @ManyToMany(cascade = CascadeType.PERSIST)
        private Collection<Role> roles = new ArrayList<>();

}
