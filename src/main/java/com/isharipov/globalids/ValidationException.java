package com.isharipov.globalids;

/**
 * Common validation exception class
 */
public class ValidationException extends IllegalArgumentException {
    public ValidationException(String message) {
        super(message);
    }
}
