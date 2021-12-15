package com.example1.carrental.exception;

public class ExistingOrderException extends RuntimeException {

        public ExistingOrderException(String message) {
                super(message);
        }

}
