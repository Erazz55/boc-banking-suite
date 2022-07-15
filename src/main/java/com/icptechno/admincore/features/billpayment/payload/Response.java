package com.icptechno.admincore.features.billpayment.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
public class Response {
    private String title;
    private Integer status;
    private Integer code;
    private String message;
}
