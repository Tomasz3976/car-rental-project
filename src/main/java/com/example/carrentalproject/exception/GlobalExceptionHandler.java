package com.example.carrentalproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import java.time.ZonedDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler({NoAccessKeyException.class, InvalidPackageException.class, UnavailableCarException.class,
                NoCreditCardException.class, ExistingOrderException.class, InsufficientFundsException.class})
        public ResponseEntity<Object> handleCustomForbiddenException(Exception e, WebRequest request) {

                ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),
                        request.getDescription(false), ZonedDateTime.now());

                return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);

        }

        @ExceptionHandler({ExistingEntityException.class, WeakPasswordException.class,
                AssignedRoleException.class})
        public ResponseEntity<Object> handleCustomBadRequestException(Exception e, WebRequest request) {

                ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),
                        request.getDescription(false), ZonedDateTime.now());

                return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

        }

        @ExceptionHandler(IllegalCallerException.class)
        public ResponseEntity<Object> handleIllegalCallerException(Exception e, WebRequest request) {

                ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),
                        request.getDescription(false), ZonedDateTime.now());

                return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);

        }

        @ExceptionHandler({EntityNotFoundException.class, UsernameNotFoundException.class})
        public ResponseEntity<Object> handleNotFoundException(Exception e, WebRequest request) {

                ErrorDetails errorDetails = new ErrorDetails(e.getMessage(),
                        request.getDescription(false), ZonedDateTime.now());

                return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

        }

}
