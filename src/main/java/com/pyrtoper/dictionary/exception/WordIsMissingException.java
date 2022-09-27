package com.pyrtoper.dictionary.exception;

public class WordIsMissingException extends RuntimeException {
    public WordIsMissingException(String message) {
        super(message);
    }
}
