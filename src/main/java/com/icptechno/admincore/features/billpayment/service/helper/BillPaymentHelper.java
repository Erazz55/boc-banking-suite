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

    final static String TOKEN = "Bearer eyJ4NXQiOiJNell4TW1Ga09HWXdNV0kwWldObU5EY3hOR1l3WW1NNFpUQTNNV0kyTkRBelpHUXpOR00wWkdSbE5qSmtPREZrWkRSaU9URmtNV0ZoTXpVMlpHVmxOZyIsImtpZCI6Ik16WXhNbUZrT0dZd01XSTBaV05tTkRjeE5HWXdZbU00WlRBM01XSTJOREF6WkdRek5HTTBaR1JsTmpKa09ERmtaRFJpT1RGa01XRmhNelUyWkdWbE5nX1JTMjU2IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJhZG1pbiIsImF1dCI6IkFQUExJQ0FUSU9OIiwiYXVkIjoiMU8xN211eHBSVXN1andUaDdhdUxHMWc3NEhzYSIsIm5iZiI6MTY1ODEzNzY5NiwiYXpwIjoiMU8xN211eHBSVXN1andUaDdhdUxHMWc3NEhzYSIsInNjb3BlIjoiZGVmYXVsdCIsImlzcyI6Imh0dHBzOlwvXC9sb2NhbGhvc3Q6OTQ0M1wvb2F1dGgyXC90b2tlbiIsImV4cCI6MTY1ODE3MzY5NiwiaWF0IjoxNjU4MTM3Njk2LCJqdGkiOiJlMDNjZTM1Ny01NDQ1LTRiOGQtYTY0OC1mODQ3MGUwZTFhZjUifQ.aYTgK44kLcGD3BY3TTDnTySwD3UgjvQfMH6eEt7oR2RJwuqUXPZFw0ouWgvZw-pJKbg7QII1qmmoshBfPMDKKobC1GsEPaRwpaCXRj2JL4aqibdmc1JPBnDUqqXi6jRaZb-3Qjg2fiFXOJQ80JKguP5EgxDC3dgKXXxhUmuoP960PHVSdndl3r3KJ_aM1JkdYpjVbZu_eOLS8WHl4doWO_qRUNJQyTfHbGxxuhUMgwG4Usz3RuLfq69YfNX0z-ohRl8fuWwk1CXnE94GmIsocx4k0d39ZvX3_dIRz-jTPtLyvPHkm-TeKYQIIxMzcOU23KxtKZ3OMIBzHOKPuknOEQ" ;

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
        headers.set("Authorization",TOKEN) ;
        HttpEntity headerRequest;

        /**Set service-provider header by considering the biller code*/
        switch (request.getBiller()){
            case "dialog":
                headers.set("service-provider", "Dialog");
                headerRequest = new HttpEntity(headers);
                System.out.println("--------------"+headers);
                return restTemplate.exchange(

                        "https://localhost:8243/dialog-bill-pay/1.0/dialog/{mobile}/check/bill",
                        HttpMethod.GET,
                        headerRequest,
                        CheckBillResponse.class,
                        request.getMobile()).getBody();

            case "mobitel":
                headers.set("service-provider", "Mobitel");
                headerRequest = new HttpEntity(headers);
                return restTemplate.exchange(
                        "https://localhost:8243/mobitel-bill-pay/1.0/mobitel/{mobile}/check/bill",
                        HttpMethod.GET,
                        headerRequest,
                        CheckBillResponse.class,
                        request.getMobile()).getBody();

            case "etisalat":
                headers.set("service-provider", "Etisalat");
                headerRequest = new HttpEntity(headers);
                return restTemplate.exchange(
                        "https://localhost:8243/etisalat-bill-pay/1.0/etisalat/{mobile}/check/bill",
                        HttpMethod.GET,
                        headerRequest,
                        CheckBillResponse.class,
                        request.getMobile()).getBody();

            default:

                return null;
        }

    }

    public Response doBillPayment(final BillPaymentRequest request){

        final DoBillPaymentRequest doBillPaymentRequest = billPaymentConversionHelper.toDoBillPaymentRequest(request);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "*/*");
        headers.set("Authorization", TOKEN);
        HttpEntity<DoBillPaymentRequest> headerRequest;

        /**Set service-provider header by considering the biller code*/
        String data = request.getBillerCode().split("-")[0];

        switch (data){
            case "dialog":
                headers.set("service-provider", "Dialog");
                headerRequest = new HttpEntity<>(doBillPaymentRequest, headers);
                return apiClient.postForObject("https://localhost:8243/dialog-bill-pay/1.0/dialog/bill/pay", headerRequest, Response.class );

            case "mobitel":
                headers.set("service-provider", "Mobitel");
                headerRequest = new HttpEntity<>(doBillPaymentRequest, headers);
                return apiClient.postForObject("https://localhost:8243/mobitel-bill-pay/1.0/mobitel/bill/pay", headerRequest, Response.class );

            case "etisalat":
                headers.set("service-provider", "Etisalat");
                headerRequest = new HttpEntity<>(doBillPaymentRequest, headers);
                return apiClient.postForObject("https://localhost:8243/etisalat-bill-pay/1.0/etisalat/bill/pay", headerRequest, Response.class );

            default:
                return null;
        }
    }
}
