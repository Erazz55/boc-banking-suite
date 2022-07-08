package com.icptechno.admincore.exception;

import com.icptechno.admincore.enums.ExceptionCode;
import lombok.Getter;

public class BaseRuntimeException extends RuntimeException {

    @Getter
    private ExceptionCode exceptionCode;

    public BaseRuntimeException(ExceptionCode exceptionCode) {

        this.exceptionCode = exceptionCode;
    }
}
