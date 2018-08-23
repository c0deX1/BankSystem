package com.company.dto;

public class PaymentDocumentRq {
    private long Sender;
    private long Receiver;
    private long Amount;

    public PaymentDocumentRq(long sender, long receiver, long amount) {
        Sender = sender;
        Receiver = receiver;
        Amount = amount;
    }

    public PaymentDocumentRq() {
    }

    public long getSender() {
        return Sender;
    }

    public void setSender(long sender) {
        Sender = sender;
    }

    public long getReceiver() {
        return Receiver;
    }

    public void setReceiver(long receiver) {
        Receiver = receiver;
    }

    public long getAmount() {
        return Amount;
    }

    public void setAmount(long amount) {
        Amount = amount;
    }
}
