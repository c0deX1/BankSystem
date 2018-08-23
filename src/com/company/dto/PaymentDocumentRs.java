package com.company.dto;

public class PaymentDocumentRs {
    private int Status;
    private String ErrorMessage;
    private PaymentDocumentRq paymentDocumentRq;

    public PaymentDocumentRs(int status, String errorMessage, PaymentDocumentRq paymentDocumentRq) {
        Status = status;
        ErrorMessage = errorMessage;
        this.paymentDocumentRq = paymentDocumentRq;
    }

    public PaymentDocumentRs(int status, String errorMessage) {
        Status = status;
        ErrorMessage = errorMessage;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public PaymentDocumentRq getPaymentDocumentRq() {
        return paymentDocumentRq;
    }

    public void setPaymentDocumentRq(PaymentDocumentRq paymentDocumentRq) {
        this.paymentDocumentRq = paymentDocumentRq;
    }

    public PaymentDocumentRs(){
    }
}
