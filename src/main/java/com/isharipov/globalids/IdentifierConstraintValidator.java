package com.isharipov.globalids;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public abstract class IdentifierConstraintValidator<A extends Annotation, I extends Identifier> implements ConstraintValidator<A, String> {
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        try {
            instance(value);
            return true;
        } catch (ValidationException ex) {
            return false;
        }
    }

    protected abstract void instance(String value);
}
