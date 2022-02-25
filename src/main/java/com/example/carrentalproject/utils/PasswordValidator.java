package com.example.carrentalproject.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    private static final String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
    private static final Pattern pattern = Pattern.compile(regex);

    private PasswordValidator() {
    }

    public static Matcher matcher(String password) {
        return pattern.matcher(password);
    }

}
