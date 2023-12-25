package com.techsavvy.security.advice;

import com.techsavvy.security.data.payload.Response;
import com.techsavvy.security.exception.ApiAccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SecurityExceptionAdvisor {

    @ExceptionHandler(ApiAccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public Response accessDenied(ApiAccessDeniedException apiAccessDeniedException) {
        return Response.builder()
                .responseMessage(apiAccessDeniedException.getMessage())
                .responseCode("403")
                .build();
    }
}
