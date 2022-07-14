package com.icptechno.admincore.features.billpayment.service.helper;

import com.icptechno.admincore.connector.APIClient;
import com.icptechno.admincore.features.billpayment.payload.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@Log
public class BillPaymentHelper {

    @Autowired
    private APIClient apiClient;

    @Autowired
    private BillPaymentConversionHelper billPaymentConversionHelper;

    public CheckBillResponse checkBillHandler(final CheckBillRequest request){

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer eyJ4NXQiOiJNell4TW1Ga09HWXdNV0kwWldObU5EY3hOR1l3WW1NNFpUQTNNV0kyTkRBelpHUXpOR00wWkdSbE5qSmtPREZrWkRSaU9URmtNV0ZoTXpVMlpHVmxOZyIsImtpZCI6Ik16WXhNbUZrT0dZd01XSTBaV05tTkRjeE5HWXdZbU00WlRBM01XSTJOREF6WkdRek5HTTBaR1JsTmpKa09ERmtaRFJpT1RGa01XRmhNelUyWkdWbE5nX1JTMjU2IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJhZG1pbiIsImF1dCI6IkFQUExJQ0FUSU9OIiwiYXVkIjoiYXRQRjVuQ0tfUnlYQ2FrRHREQ0pvWGRvTmlVYSIsIm5iZiI6MTY1Nzc2MTE2MywiYXpwIjoiYXRQRjVuQ0tfUnlYQ2FrRHREQ0pvWGRvTmlVYSIsInNjb3BlIjoiZGVmYXVsdCIsImlzcyI6Imh0dHBzOlwvXC9sb2NhbGhvc3Q6OTQ0M1wvb2F1dGgyXC90b2tlbiIsImV4cCI6MTY1Nzc2NDc2MywiaWF0IjoxNjU3NzYxMTYzLCJqdGkiOiIwZGQ3NTUzOC1kYTFmLTQ1ZGItYTU0OC02MTdkMjUyMTAxY2IifQ.NNwPnoqIVOs3GY9SX41-KlFipkl1TNpppPx-8VMTTVx9gQnFJ7bqq1rVKXqRZlZRpFPeXDrtX9pePmntX9sR6eKsEUcZRNZfMSFJTFTdnuZnGuK_vMU3jSXyronoaqen467s2UavyKT5Qx7UIZ4SlODDJmL6pqaRNP_GGOE8GACWiK0zQCXq7-mYD_wJlzhN9M_YKTaGsC5LSxjkQEj_gAejQda5FsCXjVOr1t_XwF6Lnb5XZ3HZ3355HHNVdcFW23JCZeKSaNYPl_hpmCf1AohzjukaKsFtgTBYdglwly9KZpo4m5bDE55u37pkUlwgyajHov9kA9VJugrQeqx2RA");
        //TODO set service-provider header

        /**Set service-provider header by considering the biller code*/
        switch (request.getBiller()){
            case "GSMU003900":
                headers.set("service-provider", "Dialog");
                break;

            case "GSMU004900":
                headers.set("service-provider", "Dialog");
                break;

            case "mobitel":
                headers.set("service-provider", "Mobitel");
                break;

            case "etisalat":
                headers.set("service-provider", "Etisalat");
                break;

            default:
                headers.set("service-provider", "");
        }
        System.out.println(headers.toString());
        return apiClient.getForObject( "https://localhost:8243/dialog-bill-payment/1.0.0/dialog/{mobile}/check/bill",request.getMobile(), CheckBillResponse.class );
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
