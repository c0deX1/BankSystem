package com.company.Interfaces;

import com.company.dto.PaymentDocumentRq;
import com.company.dto.PaymentDocumentRs;

import java.io.IOException;

public interface IBankController {
    PaymentDocumentRs paymentDocumentRqProcess(PaymentDocumentRq rq, boolean fromAnotherBank) throws IOException;
    String showAccountsInfo();

}
