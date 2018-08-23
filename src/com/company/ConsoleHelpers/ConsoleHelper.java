package com.company.ConsoleHelpers;

import com.company.Configurations.Config;
import com.company.Connector.MultiClientServer;
import com.company.Implementations.IAccountRepositoryImpl;
import com.company.Interfaces.IBankController;
import com.company.dto.Account;
import com.company.dto.PaymentDocumentRq;
import com.company.dto.PaymentDocumentRs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ConsoleHelper {
    public ConsoleHelper(IBankController bankController) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите свой порт: ");
        int port = Integer.parseInt(br.readLine());
        System.out.println("Введите порт другого банка: ");
        int sendPort = Integer.parseInt(br.readLine());
        Config.setPORT(port);
        Config.setSendPORT(sendPort);
        new MultiClientServer().startServerLoop(Config.getPORT());
        System.out.println("Введите !help чтобы посмотреть команды.");
        System.out.println("Введите команду: ");
        String command = "";
        while (!"quit".equals(command)) {
            command = br.readLine();
            switch (command) {
                case "!help":
                    System.out.println("!send - Перевести с одного счета на другой");
                    System.out.println("!info - Вывести информацию по счетам");
                    System.out.println("!add - Добавить счет");
                    break;
                case "!send":
                    System.out.println("Введите id отправителя ");
                    long sender = Long.parseLong(br.readLine());
                    System.out.println("Введите id получателя ");
                    long receiver = Long.parseLong(br.readLine());
                    System.out.println("Введите сумму ");
                    long money = Long.parseLong(br.readLine());
                    PaymentDocumentRs paymentDocumentRs = bankController.paymentDocumentRqProcess(new PaymentDocumentRq(sender, receiver, money), false);
                    if (paymentDocumentRs.getStatus() == 1) {
                        System.out.println("Ошибка: " + paymentDocumentRs.getErrorMessage());
                    } else {
                        System.out.println("Перевод выполнен!");
                    }
                    break;
                case "!info":
                    System.out.println("Информация по счетам: ");
                    List<Account> accounts = new IAccountRepositoryImpl().getAllAccounts();
                    for (Account acc : accounts) {
                        boolean isCorr = acc.isCorrespondent();
                        if (isCorr) {
                            System.out.println("Корреспондентский счет: ");
                        } else {
                            System.out.println("Лицевой счет: ");
                        }
                        System.out.println("Номер счета: " + acc.getId());
                        System.out.println("Баланс на счете: " + acc.getBalance());
                        if (isCorr)
                            System.out.println("Принадлежит банку: " + acc.getBankId());
                        System.out.println();
                    }
                    break;
                case "!add":
                    System.out.println("Добавление счета: ");
                    System.out.println("Введите номер счета: ");
                    long id = Long.parseLong(br.readLine());
                    System.out.println("Введите баланс: ");
                    long balance = Long.parseLong(br.readLine());
                    System.out.println("Корреспондентский счет? (y/n)");
                    String resp = br.readLine();
                    if ("y".equals(resp)) {
                        System.out.println("Введите принадлежность к банку: ");
                        long bankId = Long.parseLong(br.readLine());
                        try {
                            new IAccountRepositoryImpl().addAccount(new Account(id, balance, bankId));
                            System.out.println("Счет создан.");
                        } catch (RuntimeException ex)
                        {
                            System.out.println(ex.getMessage());
                        }
                        break;
                    } else if ("n".equals(resp)) {
                        try {
                            new IAccountRepositoryImpl().addAccount(new Account(id, balance, null));
                            System.out.println("Счет создан.");
                        } catch (RuntimeException ex) {
                            System.out.println(ex.getMessage());
                        }
                        break;
                    }
                default:
                    System.out.println("Неизвестная комманда, повторите ввод");
            }
        }
        System.out.println("Выход из приложения...");
    }
}
