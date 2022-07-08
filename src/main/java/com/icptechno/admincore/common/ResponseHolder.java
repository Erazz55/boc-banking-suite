package com.icptechno.admincore.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseHolder<T> {
    private String code;
    private String message;
    private T data;
}
