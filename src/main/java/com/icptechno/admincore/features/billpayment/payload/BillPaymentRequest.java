package com.icptechno.admincore.features.billpayment.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillPaymentRequest {
    private String accountNumber;
    private String billerCode;
    private String mobile;
    private Double amount;
}
