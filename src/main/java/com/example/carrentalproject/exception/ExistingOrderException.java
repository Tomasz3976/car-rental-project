package com.example.carrentalproject.exception;

public class ExistingOrderException extends RuntimeException {

        public ExistingOrderException(String message) {
                super(message);
        }

}
