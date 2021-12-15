package com.example1.carrental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import java.time.ZonedDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(NoAccessKeyException.class)
        public ResponseEntity<Object> handleNoAccessKeyException(NoAccessKeyException e, WebRequest request) {

                ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),
                        request.getDescription(false), ZonedDateTime.now());

                return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);

        }

        @ExceptionHandler(InvalidPackageException.class)
        public ResponseEntity<Object> handleInvalidPackageException(InvalidPackageException e, WebRequest request) {

                ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),
                        request.getDescription(false), ZonedDateTime.now());

                return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);

        }

        @ExceptionHandler(UnavailableCarException.class)
        public ResponseEntity<Object> handleUnavailableCarException(UnavailableCarException e, WebRequest request) {

                ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),
                        request.getDescription(false), ZonedDateTime.now());

                return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);

        }

        @ExceptionHandler(NoCreditCardException.class)
        public ResponseEntity<Object> handleNoCreditCardException(NoCreditCardException e, WebRequest request) {

                ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),
                        request.getDescription(false), ZonedDateTime.now());

                return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);

        }

        @ExceptionHandler(ExistingOrderException.class)
        public ResponseEntity<Object> handleExistingOrderException(ExistingOrderException e, WebRequest request) {

                ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),
                        request.getDescription(false), ZonedDateTime.now());

                return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);

        }

        @ExceptionHandler(InsufficientFundsException.class)
        public ResponseEntity<Object> handleInsufficientFundsException(InsufficientFundsException e, WebRequest request) {

                ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),
                        request.getDescription(false), ZonedDateTime.now());

                return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);

        }

        @ExceptionHandler(ExistsUserException.class)
        public ResponseEntity<Object> handleExistsUserException(ExistsUserException e, WebRequest request) {

                ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),
                        request.getDescription(false), ZonedDateTime.now());

                return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

        }

        @ExceptionHandler(WeakPasswordException.class)
        public ResponseEntity<Object> handleWeakPasswordException(WeakPasswordException e, WebRequest request) {

                ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),
                        request.getDescription(false), ZonedDateTime.now());

                return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

        }

        @ExceptionHandler(IllegalCallerException.class)
        public ResponseEntity<Object> handleIllegalCallerException(IllegalCallerException e, WebRequest request) {

                ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),
                        request.getDescription(false), ZonedDateTime.now());

                return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);

        }

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e, WebRequest request) {

                ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),
                        request.getDescription(false), ZonedDateTime.now());

                return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

        }

        @ExceptionHandler(UsernameNotFoundException.class)
        public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException e, WebRequest request) {

                ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),
                        request.getDescription(false), ZonedDateTime.now());

                return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

        }

}
