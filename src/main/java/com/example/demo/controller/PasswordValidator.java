package com.example.demo.controller;


import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.MessageFormat;
import java.util.regex.Pattern;

@Component
public class PasswordValidator implements ConstraintValidator<Password, String> {

    private static final int MIN_SIZE = 8;
    private static final int MAX_SIZE = 16;
    private static final String regexPassword = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{" + MIN_SIZE
            + "," + MAX_SIZE + "}$";
    private static final Pattern PATTERN = Pattern.compile(regexPassword);

    @Override
    public void initialize(Password constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (!matches(password)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            MessageFormat.format("최소 8자리 이상(알파벳, 숫자, 특수문자 필수)", MIN_SIZE, MAX_SIZE))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    public boolean isValid(String password) {
        return matches(password);
    }

    public boolean matches(String password) {
        return PATTERN.matcher(password).matches();
    }
}