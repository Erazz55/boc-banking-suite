package com.icptechno.admincore.features.billpayment.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DoBillPaymentRequest {
    private String mobile;
    private Double amount;
    private String billerCode;
}
