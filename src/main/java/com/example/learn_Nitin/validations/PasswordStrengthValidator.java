package com.example.learn_Nitin.validations;

import com.example.learn_Nitin.annotation.PasswordValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.*;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordValidator,String> {
    List<String> weakPasswords;
    @Override
    public void initialize(PasswordValidator constraintAnnotation) {
        weakPasswords=Arrays.asList("12345","password","qwerty");
    }

    @Override
    public boolean isValid(String passwordField, ConstraintValidatorContext context) {
        return (passwordField!=null && (!weakPasswords.contains(passwordField)));
    }
}
