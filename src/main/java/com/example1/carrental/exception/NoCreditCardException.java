package com.example1.carrental.exception;

public class NoCreditCardException extends RuntimeException {

        public NoCreditCardException(String message) {
                super(message);
        }

}
