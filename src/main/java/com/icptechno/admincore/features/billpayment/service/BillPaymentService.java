package com.icptechno.admincore.features.billpayment.service;

import com.icptechno.admincore.features.billpayment.payload.BillPaymentRequest;
import com.icptechno.admincore.features.billpayment.payload.CheckBillResponse;
import com.icptechno.admincore.features.billpayment.payload.Response;

public interface BillPaymentService {

    CheckBillResponse checkBill(final String mobile);

    Response doBillPayment(final BillPaymentRequest request);
}
