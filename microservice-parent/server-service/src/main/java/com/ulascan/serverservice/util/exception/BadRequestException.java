package com.ulascan.serverservice.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends BaseException {

    public BadRequestException(String errorCode, String errorMessage, Object... parameters) {
        super(errorCode, String.format(errorMessage, parameters));
    }

}
