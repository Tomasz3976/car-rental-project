package com.example1.carrental.repo;

import com.example1.carrental.domain.User;
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
class UserRepoTest {

        @Autowired
        private UserRepo underTest;

        @BeforeEach
        void setUp() {
                User user = new User(null, "Lawrence", "Okolie", "lawrence123", "apples678", "lpf56@gmail.com", 888777621, null, new ArrayList<>());
                User user2 = new User(null, "Anthony", "Joshua", "joshua456", "eddiehearn678", "gbjosh@onet.pl", 465999222, null, new ArrayList<>());
                User user3 = new User(null, "Tyson", "Fury", "gypsyKing", "deeoonthai", "gypsyKing655@gmail.com", 765555444, null, new ArrayList<>());
                User user4 = new User(null, "Saul", "Alvarez", "canelo", "prettyBoy", "Goose@gmail.com", 908764220, null, new ArrayList<>());
                underTest.save(user);
                underTest.save(user2);
                underTest.save(user3);
                underTest.save(user4);
        }

        @AfterEach
        void tearDown() {
                underTest.deleteAll();
        }

        @Test
        void itShouldReturnTrueIfUsernameExists() {
                assertThat(underTest.findByUsername("lawrence123")).isInstanceOf(Optional.class).isPresent();
                assertThat(underTest.findByUsername("Gregory56")).isInstanceOf(Optional.class).isEmpty();
                assertThat(underTest.findByUsername("gypsyKing")).isInstanceOf(Optional.class).isPresent();
                assertThat(underTest.findByUsername("joshua456")).isInstanceOf(Optional.class).isPresent();
                assertThat(underTest.findByUsername("programmer777")).isInstanceOf(Optional.class).isEmpty();
                assertThat(underTest.findByUsername("canelo")).isInstanceOf(Optional.class).isPresent();

        }

}