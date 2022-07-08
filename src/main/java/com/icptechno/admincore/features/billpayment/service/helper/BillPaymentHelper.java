package com.icptechno.admincore.features.billpayment.service.helper;

import com.icptechno.admincore.connector.APIClient;
import com.icptechno.admincore.features.billpayment.payload.BillPaymentRequest;
import com.icptechno.admincore.features.billpayment.payload.CheckBillResponse;
import com.icptechno.admincore.features.billpayment.payload.DoBillPaymentRequest;
import com.icptechno.admincore.features.billpayment.payload.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class BillPaymentHelper {

    @Autowired
    private APIClient apiClient;

    @Autowired
    private BillPaymentConversionHelper billPaymentConversionHelper;

    public CheckBillResponse checkBillHandler(final String mobile){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        return apiClient.getForObject( "http://localhost:8081/dialog-provider/dialog/{mobile}/check/bill", mobile, CheckBillResponse.class );
    }

    public Response doBillPayment(final BillPaymentRequest request){
        //TODO deduct amount from the bank account

        final DoBillPaymentRequest doBillPaymentRequest = billPaymentConversionHelper.toDoBillPaymentRequest(request);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<DoBillPaymentRequest> headerRequest = new HttpEntity<>(doBillPaymentRequest, headers);
        return apiClient.postForObject("http://localhost:8081/dialog-provider/dialog/bill/pay", headerRequest, Response.class );
    }
}
