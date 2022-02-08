package com.adgarsolutions.shared.exception;

public class InvalidNewOrderException extends Exception {
    public InvalidNewOrderException(String msg) {
        super(msg);
    }

    public InvalidNewOrderException(String msg, Throwable ex) {
        super(msg, ex);
    }
}