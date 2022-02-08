package com.adgarsolutions.exceptionhandlers;

import com.adgarsolutions.shared.exception.InvalidNewOrderException;
import com.adgarsolutions.shared.model.CustomError;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes={InvalidNewOrderException.class, ExceptionHandler.class})
public class InvalidNewOrderExceptionHandler implements ExceptionHandler<InvalidNewOrderException, HttpResponse<CustomError>> {

    @Override
    public HttpResponse<CustomError> handle(HttpRequest request, InvalidNewOrderException exception) {
        return HttpResponse.badRequest(new CustomError(HttpStatus.BAD_REQUEST, "INVALID_NEW_ORD_EXCEPTION", exception.getMessage()));
    }
}
