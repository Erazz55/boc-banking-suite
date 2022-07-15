package com.icptechno.admincore.features.billpayment.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckBillResponse extends Response{
    private CheckBillResponseData data;
}
