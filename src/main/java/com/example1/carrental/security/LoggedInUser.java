package com.example1.carrental.security;

import com.example1.carrental.domain.User;
import com.example1.carrental.repo.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoggedInUser {

        private final UserRepo userRepo;

        public LoggedInUser(UserRepo userRepo) {
                this.userRepo = userRepo;
        }

        public User getUser() {

                Authentication principal = SecurityContextHolder.getContext().getAuthentication();
                String username = principal.getName();
                return userRepo.findByUsername(username).orElseThrow();

        }

}
