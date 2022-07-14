package com.icptechno.admincore.features.billpayment.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DoCheckBillRequest {
    private String biller;
    private String mobile;
}
