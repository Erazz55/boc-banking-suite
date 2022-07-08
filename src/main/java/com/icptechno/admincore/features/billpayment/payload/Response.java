package com.icptechno.admincore.features.billpayment.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private String title;
    private Integer status;
    private Integer code;
    private String message;
}
