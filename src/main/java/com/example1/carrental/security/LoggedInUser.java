package com.example1.carrental.security;

import com.example1.carrental.domain.User;
import com.example1.carrental.repo.UserRepo;
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

                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String username = ((UserDetails) principal).getUsername();
                return userRepo.findByUsername(username).orElseThrow();

        }

}
