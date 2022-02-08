package com.adgarsolutions.exceptionhandlers;

import com.adgarsolutions.shared.exception.InvalidAccountIdException;
import com.adgarsolutions.shared.model.CustomError;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes={InvalidAccountIdException.class, ExceptionHandler.class})
public class InvalidAccountIdExceptionHandler implements ExceptionHandler<InvalidAccountIdException, HttpResponse<CustomError>> {

    @Override
    public HttpResponse<CustomError> handle(HttpRequest request, InvalidAccountIdException exception) {
        return HttpResponse.badRequest(new CustomError(HttpStatus.BAD_REQUEST, "INVALID_ACCOUNT_ID_EXCEPTION", exception.getMessage()));
    }
}
