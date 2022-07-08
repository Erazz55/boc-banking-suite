package com.icptechno.admincore.enums;

public enum ExceptionCode {
    CLIENT_CONNECTION_EXCEPTION( "ex-001");
    private String code;

    ExceptionCode(String code) {

        this.code = code;
    }
}
