package com.example.carrentalproject.service;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Data
public class PasswordValidator {

    private static final String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
    private static final Pattern pattern = Pattern.compile(regex);

    public static Matcher matcher(String password) {
        return pattern.matcher(password);
    }

}
