package com.diotto.petshelter.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponse> handlerResourceNotFoundException(ResourceNotFound exception, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<ErrorResponse> handlerResourceBadRequestException(BadRequest exception, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerException(Exception exception, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handlerResourceBadRequestException(NoHandlerFoundException exception, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }



}
