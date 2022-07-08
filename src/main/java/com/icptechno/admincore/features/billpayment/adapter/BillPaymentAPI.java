package com.icptechno.admincore.features.billpayment.adapter;

import com.icptechno.admincore.features.billpayment.payload.BillPaymentRequest;
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

    @GetMapping(value = "/{mobile}")
    public CheckBillResponse checkBill(@PathVariable String mobile){
        return billPaymentService.checkBill(mobile);
    }

    @PostMapping(value = "")
    public Response doBillPayment(@RequestBody BillPaymentRequest request){
        return billPaymentService.doBillPayment(request);
    }
}
