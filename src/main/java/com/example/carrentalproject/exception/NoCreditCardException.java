package com.example.carrentalproject.exception;

public class NoCreditCardException extends RuntimeException {

        public NoCreditCardException(String message) {
                super(message);
        }

}
