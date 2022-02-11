package com.adgarsolutions.shared.exception;

public class ItemNotFoundInDatabaseException extends Exception {
    public ItemNotFoundInDatabaseException(String msg) {
        super(msg);
    }

    public ItemNotFoundInDatabaseException(String msg, Throwable t) {
        super(msg, t);
    }
}
