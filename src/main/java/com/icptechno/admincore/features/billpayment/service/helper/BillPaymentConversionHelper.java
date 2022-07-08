package com.icptechno.admincore.features.billpayment.service.helper;

import com.icptechno.admincore.features.billpayment.payload.BillPaymentRequest;
import com.icptechno.admincore.features.billpayment.payload.DoBillPaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BillPaymentConversionHelper {

    /**Convert request to DoBillPaymentRequest*/
    DoBillPaymentRequest toDoBillPaymentRequest(final BillPaymentRequest request){
        return DoBillPaymentRequest
                .builder()
                .amount(request.getAmount())
                .billerCode(request.getBillerCode())
                .mobile(request.getMobile())
                .build();
    }
}
