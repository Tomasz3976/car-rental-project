package com.example1.carrental.repo;

import com.example1.carrental.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {

        Optional<Role> findByName(String name);

}
