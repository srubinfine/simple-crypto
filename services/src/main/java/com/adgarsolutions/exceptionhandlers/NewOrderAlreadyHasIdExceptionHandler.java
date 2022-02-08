package com.adgarsolutions.exceptionhandlers;

import com.adgarsolutions.shared.exception.NewOrderAlreadyHasIdException;
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
@Requires(classes={NewOrderAlreadyHasIdException.class, ExceptionHandler.class})
public class NewOrderAlreadyHasIdExceptionHandler implements ExceptionHandler<NewOrderAlreadyHasIdException, HttpResponse<CustomError>> {

    @Override
    public HttpResponse<CustomError> handle(HttpRequest request, NewOrderAlreadyHasIdException exception) {
        return HttpResponse.badRequest(new CustomError(HttpStatus.BAD_REQUEST, "NEW_ORD_ALREADY_HAD_ID_EXCEPTION", exception.getMessage()));
    }
}
