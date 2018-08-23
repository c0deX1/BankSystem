package com.company.Validators;

import com.company.dto.Account;
import com.company.dto.PaymentDocumentRq;

public final class Validator {
    public static boolean validate(Account sender, PaymentDocumentRq rq){
        if (sender.getBalance() < rq.getAmount()) {
            return false;
        }
        return true;
    }
}