package com.example.backend.controller;

import com.example.backend.dto.ErrorDto;
import com.example.backend.exceptions.LocalizedApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springfox.documentation.annotations.ApiIgnore;

import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.backend.exceptions.ErrorCode.VALIDATION_ERROR;

@Slf4j
@ApiIgnore
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LocalizedApplicationException.class)
    public ErrorDto handleCustomException(LocalizedApplicationException ex) {
        log.error("Application exception: ", ex);
        String localizationCode = "errors." + ex.getErrorCode().name().toLowerCase();
        Serializable[] params = ex.getParams();

        return error(localizationCode, params);
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorDto handleAuthException(AccessDeniedException ex) {
        log.error("Access denied exception: ", ex);
        return error(ex.getMessage(), new Serializable[0]);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto validationException(final MethodArgumentNotValidException ex) {
        log.error("Bean validation exception: ", ex);
        String localizationCode = getCode("errors", VALIDATION_ERROR.name().toLowerCase());

        return error(localizationCode, ex.getBindingResult().getAllErrors().stream()
                .map(t -> ((FieldError) t).getField() + ": " + t.getDefaultMessage()).toList()
                .toArray(new Serializable[]{}));
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorDto handleCustomException(Exception ex) {
        log.error("System exception:", ex);

        return error(ex.getMessage(), new Serializable[0]);
    }

    private static String getCode(String... strings) {
        return Stream.of(strings)
                .filter((t) -> !ObjectUtils.isEmpty(t))
                .collect(Collectors.joining("."));
    }

    protected static ErrorDto error(String errorCode, Serializable[] params) {
        return new ErrorDto(errorCode, Stream.of(params).map(Object::toString).collect(Collectors.toList()));
    }
}
