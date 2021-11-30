package com.example1.carrental.repo;

import com.example1.carrental.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

        Optional<User> findByUsername(String username);

}
