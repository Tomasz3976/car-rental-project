package com.example.carrentalproject.repository;

import com.example.carrentalproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

        Optional<User> findByUsername(String username);

}
