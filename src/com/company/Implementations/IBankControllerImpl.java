package com.company.Implementations;

import com.company.Configurations.Config;
import com.company.Connector.Sender;
import com.company.Interfaces.IAccountRepository;
import com.company.Interfaces.IBankController;
import com.company.Validators.Validator;
import com.company.dto.Account;
import com.company.dto.PaymentDocumentRq;
import com.company.dto.PaymentDocumentRs;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


public class IBankControllerImpl implements IBankController
{
    IAccountRepository repository;

    public IBankControllerImpl(IAccountRepository repository){
        this.repository = repository;
    }

    @Override
    public PaymentDocumentRs paymentDocumentRqProcess(PaymentDocumentRq rq, boolean fromAnotherBank) throws IOException {
        Account sender = repository.getById(rq.getSender());
        Account receiver = repository.getById(rq.getReceiver());
        try {
            if (sender == null) {
                if (receiver == null) {
                    return noOneInBank(rq);
                } else {
                    return receiverInBank(receiver, rq, fromAnotherBank);
                }
            } else {
                if (receiver == null) {
                    return senderInBank(sender, rq, fromAnotherBank);
                } else {
                    return bothOnBank(sender, receiver, rq);
                }
            }
        } catch (RuntimeException ex) {
            return new PaymentDocumentRs(1, ex.getMessage());
        }
    }

    @Override
    public String showAccountsInfo() {
        return null;
    }

    private void updateRepository(Account sender, Account receiver) throws IOException {
        repository.updateAccount(sender);
        repository.updateAccount(receiver);
    }

    private void commitTransaction(Account sender, Account receiver, PaymentDocumentRq rq) throws IOException {
        sender.setBalance(sender.getBalance() - rq.getAmount());
        receiver.setBalance(receiver.getBalance() + rq.getAmount());
        updateRepository(sender, receiver);
    }

    public PaymentDocumentRs rollBackTransaction(PaymentDocumentRq rq) throws IOException { //Для исключительной ситуации receiver in bank
        Account sender = repository.getCorrespondent();
        Account receiver = repository.getById(rq.getReceiver());
        sender.setBalance(sender.getBalance() + rq.getAmount());
        receiver.setBalance(receiver.getBalance() - rq.getAmount());
        updateRepository(sender, receiver);
        return new PaymentDocumentRs(0,"Откат транзакции " + rq.getSender() + "->" + rq.getReceiver());
    }

    private PaymentDocumentRs bothOnBank(Account sender, Account receiver, PaymentDocumentRq rq) throws IOException {
        if (Validator.validate(repository.getById(rq.getSender()), rq)){
            commitTransaction(sender, receiver, rq);
            return new PaymentDocumentRs(0, "Перевод выполнен!");
        } else {
            throw new RuntimeException("Недостаточно средств для перевода");
        }
    }
    private PaymentDocumentRs senderInBank(Account sender, PaymentDocumentRq rq, boolean fromAnotherBank) throws IOException {
        if (Validator.validate(sender, rq)){
            if (!fromAnotherBank)
                Sender.Send(new ObjectMapper().writeValueAsString(rq), Config.getSendPORT());
            sender.setBalance(sender.getBalance() - rq.getAmount());
            Account receiver = repository.getCorrespondent();
            commitTransaction(sender, receiver, rq);
        } else {
            if (fromAnotherBank) {
                return new PaymentDocumentRs(1, "Недостаточно средств для перевода в другом банке", rq);
            } else {
                throw new RuntimeException("Недостаточно средств для перевода");
            }
        }
        return new PaymentDocumentRs(0, "Перевод отправлен в другой банк..");
    }

    private PaymentDocumentRs receiverInBank(Account receiver, PaymentDocumentRq rq, boolean fromAnotherBank) throws IOException {
        Account sender = repository.getCorrespondent();
        if (Validator.validate(sender, rq)) {
            if (!fromAnotherBank) {
                Sender.Send(new ObjectMapper().writeValueAsString(rq), Config.getSendPORT());
            }
            commitTransaction(sender, receiver, rq);
        } else {
            throw new RuntimeException("Недостаточно средств для перевода на корреспондентском счете");
        }
        return new PaymentDocumentRs(0, "Перевод получен!");
    }

    private PaymentDocumentRs noOneInBank(PaymentDocumentRq rq) throws JsonProcessingException {
        Sender.Send(new ObjectMapper().writeValueAsString(rq), Config.getSendPORT());
        return new PaymentDocumentRs(0, "Перевод отправлен в другой банк..");
    }
}
