package com.icptechno.admincore.exception;

import com.icptechno.admincore.enums.ExceptionCode;

public class ClientConnectionException extends BaseRuntimeException {

    public ClientConnectionException() {
        super(ExceptionCode.CLIENT_CONNECTION_EXCEPTION);
    }

}
