package com.icptechno.admincore.features.billpayment.service.impl;

import com.icptechno.admincore.features.billpayment.payload.BillPaymentRequest;
import com.icptechno.admincore.features.billpayment.payload.CheckBillResponse;
import com.icptechno.admincore.features.billpayment.payload.Response;
import com.icptechno.admincore.features.billpayment.service.BillPaymentService;
import com.icptechno.admincore.features.billpayment.service.helper.BillPaymentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BillPaymentImpl implements BillPaymentService {

    @Autowired
    private BillPaymentHelper billPaymentHelper;

    /**Check bill payment by passing mobile number*/
    @Override
    public CheckBillResponse checkBill( final String mobile) {
        return billPaymentHelper.checkBillHandler(mobile);
    }


    /**Do bill payment */
    @Override
    public Response doBillPayment(final BillPaymentRequest request) {
        return billPaymentHelper.doBillPayment(request);
    }
}
