package com.adgarsolutions.shared.exception;

public class EmptyDatabaseCollectionException extends Exception {
    public EmptyDatabaseCollectionException(String msg) {
        super(msg);
    }

    public EmptyDatabaseCollectionException(String msg, Throwable t) {
        super(msg, t);
    }
}
