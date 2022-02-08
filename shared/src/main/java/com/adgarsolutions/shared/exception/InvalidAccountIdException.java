package com.adgarsolutions.shared.exception;

public class InvalidAccountIdException extends Exception {
    public InvalidAccountIdException(String msg) {
        super(msg);
    }

    public InvalidAccountIdException(String msg, Throwable ex) {
        super(msg, ex);
    }
}