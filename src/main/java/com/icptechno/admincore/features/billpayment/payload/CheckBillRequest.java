package com.icptechno.admincore.features.billpayment.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckBillRequest {
    private String biller;
    private String mobile;
}
