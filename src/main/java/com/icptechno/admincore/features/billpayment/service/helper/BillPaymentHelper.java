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

    final static String TOKEN = "Bearer eyJ4NXQiOiJNell4TW1Ga09HWXdNV0kwWldObU5EY3hOR1l3WW1NNFpUQTNNV0kyTkRBelpHUXpOR00wWkdSbE5qSmtPREZrWkRSaU9URmtNV0ZoTXpVMlpHVmxOZyIsImtpZCI6Ik16WXhNbUZrT0dZd01XSTBaV05tTkRjeE5HWXdZbU00WlRBM01XSTJOREF6WkdRek5HTTBaR1JsTmpKa09ERmtaRFJpT1RGa01XRmhNelUyWkdWbE5nX1JTMjU2IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJhZG1pbiIsImF1dCI6IkFQUExJQ0FUSU9OIiwiYXVkIjoiMU8xN211eHBSVXN1andUaDdhdUxHMWc3NEhzYSIsIm5iZiI6MTY1Nzg3NTM0MCwiYXpwIjoiMU8xN211eHBSVXN1andUaDdhdUxHMWc3NEhzYSIsInNjb3BlIjoiZGVmYXVsdCIsImlzcyI6Imh0dHBzOlwvXC9sb2NhbGhvc3Q6OTQ0M1wvb2F1dGgyXC90b2tlbiIsImV4cCI6MTY1NzkxMTM0MCwiaWF0IjoxNjU3ODc1MzQwLCJqdGkiOiIzYjU3OTIwYi0wMGRmLTQ5NDQtOTg2Yi1iYTkyODkyZWNiZjMifQ.g_9h76rsi_FQx88qXyoYL8SDTs3VQwVU_AJoJV7LDjbCL8_tJgVzCfc-5oXx63liAczrkkbiCCTKI67vjJAHgNTZRVuAbALWJmI0IvbYFfe0kKqhhKnw5eq-HyXlrGFa47HCWBzH9GM4j95_2ybP04DByACb6XyyVEm1LcL7Fzxq6ud57UeAjOYmcJD8Wq5zKLX1VSIYV9UDMgU3ZhncMDIN4u7-clScTfNqD2uKPvhkd_Y9Bs05bjYdHSwUdZDeZgIKgVaTIUvUBFlNnclxYO87DeAvK-D2qdH1wrbwVAduo_rlJsd8ejsEqQR4W4wwA6fqcA8ySlN07XV2ni1ENg" ;

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
                "https://localhost:8243/dialog-bill-pay/1.0/dialog/{mobile}/check/bill",
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
        headers.set("Authorization", TOKEN);

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
        return apiClient.postForObject("https://localhost:8243/dialog-bill-pay/1.0/dialog/bill/pay", headerRequest, Response.class );
    }

}
