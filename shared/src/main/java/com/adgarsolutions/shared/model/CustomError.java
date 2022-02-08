package com.adgarsolutions.shared.model;

import io.micronaut.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomError {
    private final HttpStatus status;
    private final String code;
    private final String error;
}
