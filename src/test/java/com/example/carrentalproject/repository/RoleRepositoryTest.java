package com.example.carrentalproject.repository;

import com.example.carrentalproject.domain.Role;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest {

        @Autowired
        private RoleRepository roleRepository;

        @BeforeEach
        void setUp() {
                Role role = new Role(null, "ROLE_ADMIN", new ArrayList<>());
                Role role2 = new Role(null, "ROLE_USER", new ArrayList<>());
                roleRepository.save(role);
                roleRepository.save(role2);
        }

        @AfterEach
        void tearDown() {
                roleRepository.deleteAll();
        }

        @Test
        void itShouldFindRoleByName() {

                AssertionsForClassTypes.assertThat(roleRepository.findByName("ROLE_ADMIN")).isInstanceOf(Optional.class).isPresent();
                AssertionsForClassTypes.assertThat(roleRepository.findByName("ROLE_MANAGER")).isInstanceOf(Optional.class).isEmpty();
                AssertionsForClassTypes.assertThat(roleRepository.findByName("ROLE_USER")).isInstanceOf(Optional.class).isPresent();
                AssertionsForClassTypes.assertThat(roleRepository.findByName("ROLE_SUPER_ADMIN")).isInstanceOf(Optional.class).isEmpty();

        }

}