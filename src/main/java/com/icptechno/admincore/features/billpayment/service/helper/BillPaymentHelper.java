package com.icptechno.admincore.features.billpayment.service.helper;

import com.icptechno.admincore.connector.APIClient;
import com.icptechno.admincore.features.billpayment.payload.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Log
public class BillPaymentHelper {

//    final static String TOKEN = ;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private APIClient apiClient;

    @Autowired
    private BillPaymentConversionHelper billPaymentConversionHelper;

    public CheckBillResponse checkBillHandler(final CheckBillRequest request){

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "*/*");
        headers.set("Authorization", "Bearer eyJ4NXQiOiJNell4TW1Ga09HWXdNV0kwWldObU5EY3hOR1l3WW1NNFpUQTNNV0kyTkRBelpHUXpOR00wWkdSbE5qSmtPREZrWkRSaU9URmtNV0ZoTXpVMlpHVmxOZyIsImtpZCI6Ik16WXhNbUZrT0dZd01XSTBaV05tTkRjeE5HWXdZbU00WlRBM01XSTJOREF6WkdRek5HTTBaR1JsTmpKa09ERmtaRFJpT1RGa01XRmhNelUyWkdWbE5nX1JTMjU2IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJhZG1pbiIsImF1dCI6IkFQUExJQ0FUSU9OIiwiYXVkIjoiYXRQRjVuQ0tfUnlYQ2FrRHREQ0pvWGRvTmlVYSIsIm5iZiI6MTY1Nzg2NzI2OSwiYXpwIjoiYXRQRjVuQ0tfUnlYQ2FrRHREQ0pvWGRvTmlVYSIsInNjb3BlIjoiZGVmYXVsdCIsImlzcyI6Imh0dHBzOlwvXC9sb2NhbGhvc3Q6OTQ0M1wvb2F1dGgyXC90b2tlbiIsImV4cCI6MTY1Nzg3MDg2OSwiaWF0IjoxNjU3ODY3MjY5LCJqdGkiOiIzZDg2ODYwMy05YzBmLTRiNTctYjA2MS0xNzNmZDI0NjVkMzIifQ.hNLTwOTJem6PxOTNwaJUgpTLrrC4iyjz-sL-AZK5Dx4bVjEvo3ChJs6eV5KssbGXu4oUUC5zxin24rgqP9eoiz-BIlZfPfk73uK7pSPmzYSd2BUwgSrbP4F3ss0dapZTuN2DCvPP1lYtIOCQ9mMta3_bAOlyewCC5Vu02PJOrKJSC0qEySEwx6gZbEke9nC2m1tXlxVnwZdef21otHbPJ81SLGVgRuZ0TVbDS39UQM97-YILe2qYvEokxdY_lwiKgFHLdXWP0c82sinLUX2XmUNHDAmra-M3Al1TA4l-iCVW6xfa2EzD8dHZ1oaMmWPUbTsyxE6i20gtek9ZnVA8-Q");

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

        HttpEntity headerRequest = new HttpEntity(headers);

        System.out.println(headerRequest);

        final ResponseEntity<CheckBillResponse> exchange = restTemplate.exchange(
                "https://localhost:8243/dialog-bill-payment/1.0.0/dialog/{mobile}/check/bill",
                HttpMethod.GET,
                headerRequest,
                CheckBillResponse.class,
                request.getMobile());

        final CheckBillResponse response = new CheckBillResponse();
        response.setData(exchange.getBody().getData());
        response.setCode(9000);
        response.setMessage("Done");
        response.setTitle("SUCCESS");

        return response;
    }

    public Response doBillPayment(final BillPaymentRequest request){
        //TODO deduct amount from the bank account

        final DoBillPaymentRequest doBillPaymentRequest = billPaymentConversionHelper.toDoBillPaymentRequest(request);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "*/*");
        headers.set("Authorization", "Bearer eyJ4NXQiOiJNell4TW1Ga09HWXdNV0kwWldObU5EY3hOR1l3WW1NNFpUQTNNV0kyTkRBelpHUXpOR00wWkdSbE5qSmtPREZrWkRSaU9URmtNV0ZoTXpVMlpHVmxOZyIsImtpZCI6Ik16WXhNbUZrT0dZd01XSTBaV05tTkRjeE5HWXdZbU00WlRBM01XSTJOREF6WkdRek5HTTBaR1JsTmpKa09ERmtaRFJpT1RGa01XRmhNelUyWkdWbE5nX1JTMjU2IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJhZG1pbiIsImF1dCI6IkFQUExJQ0FUSU9OIiwiYXVkIjoiYXRQRjVuQ0tfUnlYQ2FrRHREQ0pvWGRvTmlVYSIsIm5iZiI6MTY1Nzg2NzI2OSwiYXpwIjoiYXRQRjVuQ0tfUnlYQ2FrRHREQ0pvWGRvTmlVYSIsInNjb3BlIjoiZGVmYXVsdCIsImlzcyI6Imh0dHBzOlwvXC9sb2NhbGhvc3Q6OTQ0M1wvb2F1dGgyXC90b2tlbiIsImV4cCI6MTY1Nzg3MDg2OSwiaWF0IjoxNjU3ODY3MjY5LCJqdGkiOiIzZDg2ODYwMy05YzBmLTRiNTctYjA2MS0xNzNmZDI0NjVkMzIifQ.hNLTwOTJem6PxOTNwaJUgpTLrrC4iyjz-sL-AZK5Dx4bVjEvo3ChJs6eV5KssbGXu4oUUC5zxin24rgqP9eoiz-BIlZfPfk73uK7pSPmzYSd2BUwgSrbP4F3ss0dapZTuN2DCvPP1lYtIOCQ9mMta3_bAOlyewCC5Vu02PJOrKJSC0qEySEwx6gZbEke9nC2m1tXlxVnwZdef21otHbPJ81SLGVgRuZ0TVbDS39UQM97-YILe2qYvEokxdY_lwiKgFHLdXWP0c82sinLUX2XmUNHDAmra-M3Al1TA4l-iCVW6xfa2EzD8dHZ1oaMmWPUbTsyxE6i20gtek9ZnVA8-Q");

        /**Set service-provider header by considering the biller code*/
        switch (request.getBillerCode()){
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

        HttpEntity<DoBillPaymentRequest> headerRequest = new HttpEntity<>(doBillPaymentRequest, headers);
        return apiClient.postForObject("https://localhost:8243/dialog-bill-payment/1.0.0/dialog/bill/pay", headerRequest, Response.class );
    }

}
