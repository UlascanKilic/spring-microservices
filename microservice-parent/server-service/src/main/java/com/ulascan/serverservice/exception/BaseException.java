package com.ulascan.serverservice.exception;

import lombok.Getter;

import java.util.Objects;

@Getter
public class BaseException extends RuntimeException{

    private final String field;
    private final String code;
    private final String message;

    public BaseException(String errorCode, String message) {
        this("", errorCode, message);
    }

    public BaseException(String field, String errorCode, String message) {
        super(message);
        Objects.requireNonNull(errorCode);
        this.code = errorCode;
        this.field = field;
        this.message = message;
    }
}
