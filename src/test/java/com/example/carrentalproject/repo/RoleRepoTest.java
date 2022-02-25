package com.example.carrentalproject.repo;

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
class RoleRepoTest {

        @Autowired
        private RoleRepo underTest;

        @BeforeEach
        void setUp() {
                Role role = new Role(null, "ROLE_ADMIN", new ArrayList<>());
                Role role2 = new Role(null, "ROLE_USER", new ArrayList<>());
                underTest.save(role);
                underTest.save(role2);
        }

        @AfterEach
        void tearDown() {
                underTest.deleteAll();
        }

        @Test
        void itShouldFindRoleByName() {

                AssertionsForClassTypes.assertThat(underTest.findByName("ROLE_ADMIN")).isInstanceOf(Optional.class).isPresent();
                AssertionsForClassTypes.assertThat(underTest.findByName("ROLE_MANAGER")).isInstanceOf(Optional.class).isEmpty();
                AssertionsForClassTypes.assertThat(underTest.findByName("ROLE_USER")).isInstanceOf(Optional.class).isPresent();
                AssertionsForClassTypes.assertThat(underTest.findByName("ROLE_SUPER_ADMIN")).isInstanceOf(Optional.class).isEmpty();

        }

}