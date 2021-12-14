package com.example1.carrental.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class ErrorDetails {

        private final String message;
        private final String details;
        private final ZonedDateTime timestamp;

}