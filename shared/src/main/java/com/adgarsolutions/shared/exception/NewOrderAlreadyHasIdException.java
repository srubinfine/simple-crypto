package com.adgarsolutions.shared.exception;

public class NewOrderAlreadyHasIdException extends Exception {
    public NewOrderAlreadyHasIdException(String msg) {
        super(msg);
    }

    public NewOrderAlreadyHasIdException(String msg, Throwable ex) {
        super(msg, ex);
    }
}