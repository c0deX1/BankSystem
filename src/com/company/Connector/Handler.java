package com.company.Connector;

import com.company.Configurations.Config;
import com.company.Implementations.IAccountRepositoryImpl;
import com.company.Implementations.IBankControllerImpl;
import com.company.dto.PaymentDocumentRq;
import com.company.dto.PaymentDocumentRs;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Handler extends Thread {
    private Socket socket;
    private BufferedReader reader;


    public Handler(Socket s) throws IOException {

        socket = s;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        String message = dis.readUTF();

        if (message.contains("cash") && !message.contains("errorMessage")) {
            PaymentDocumentRq request = new ObjectMapper().readValue(message, PaymentDocumentRq.class);
            System.out.println("Запрос из другого банка");
            PaymentDocumentRs response = new IBankControllerImpl(new IAccountRepositoryImpl()).paymentDocumentRqProcess(request, true);
            if (response.getStatus() == 1) {
                Sender.Send(new ObjectMapper().writeValueAsString(response), Config.getSendPORT());
            } else {
                System.out.println("Перевод выполнен!");
            }
            System.out.println("Запрос обработан");
        } else {
            PaymentDocumentRs respose = new ObjectMapper().readValue(message, PaymentDocumentRs.class);
            if (respose.getPaymentDocumentRq() == null) {
                System.out.println("Ответ из другого банка: ");
                System.out.println(respose.getErrorMessage());
            } else {
                PaymentDocumentRs response = new IBankControllerImpl(new IAccountRepositoryImpl()).rollBackTransaction(respose.getPaymentDocumentRq());
                System.out.println("Ответ из другого банка: ");
                System.out.println(respose.getErrorMessage());
                System.out.println(response.getErrorMessage());
            }
        }
        start();
    }
}