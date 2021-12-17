package com.example1.carrental.repo;

import com.example1.carrental.domain.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

                assertThat(underTest.findByName("ROLE_ADMIN")).isInstanceOf(Optional.class).isPresent();
                assertThat(underTest.findByName("ROLE_MANAGER")).isInstanceOf(Optional.class).isEmpty();
                assertThat(underTest.findByName("ROLE_USER")).isInstanceOf(Optional.class).isPresent();
                assertThat(underTest.findByName("ROLE_SUPER_ADMIN")).isInstanceOf(Optional.class).isEmpty();

        }

}