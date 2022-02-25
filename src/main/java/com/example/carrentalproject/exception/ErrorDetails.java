package com.example.carrentalproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class ErrorDetails {

        private final String message;
        private final String details;
        private final ZonedDateTime timestamp;

}
