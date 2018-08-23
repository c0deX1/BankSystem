package com.company;


import com.company.ConsoleHelpers.ConsoleHelper;
import com.company.Helpers.FileHelper;
import com.company.Implementations.IAccountRepositoryImpl;
import com.company.Implementations.IBankControllerImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileHelper.setPATH(args[0]);
        System.out.println("Указанный путь: " + FileHelper.getPATH());
        new ConsoleHelper(new IBankControllerImpl(new IAccountRepositoryImpl()));
    }
}