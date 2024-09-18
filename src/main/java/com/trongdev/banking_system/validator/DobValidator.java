package com.trongdev.banking_system.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.sql.Timestamp;

public class DobValidator implements ConstraintValidator<DobConstraint, Timestamp> {
    @Override
    public boolean isValid(Timestamp timestamp, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }

    @Override
    public void initialize(DobConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
