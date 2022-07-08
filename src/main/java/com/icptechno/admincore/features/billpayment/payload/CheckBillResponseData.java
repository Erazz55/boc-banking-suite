package com.icptechno.admincore.features.billpayment.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckBillResponseData {
    private String mobile;
    private String balance;
    private String billAmount;
}
