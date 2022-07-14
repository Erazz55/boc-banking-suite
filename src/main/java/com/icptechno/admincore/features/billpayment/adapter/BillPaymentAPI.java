package com.icptechno.admincore.features.billpayment.adapter;

import com.icptechno.admincore.features.billpayment.payload.BillPaymentRequest;
import com.icptechno.admincore.features.billpayment.payload.CheckBillRequest;
import com.icptechno.admincore.features.billpayment.payload.CheckBillResponse;
import com.icptechno.admincore.features.billpayment.payload.Response;
import com.icptechno.admincore.features.billpayment.service.BillPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bill-payment")
public class BillPaymentAPI {

    @Autowired
    private BillPaymentService billPaymentService;

    @PostMapping(value = "/check-bill")
    public CheckBillResponse checkBill(@RequestBody CheckBillRequest request){
        return billPaymentService.checkBill(request);
    }

    @PostMapping(value = "")
    public Response doBillPayment(@RequestBody BillPaymentRequest request){
        return billPaymentService.doBillPayment(request);
    }
}
