package com.company.Helpers;

import com.company.dto.Account;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public final class FileHelper {

    private static String PATH = "Accounts.txt";

    public static List<Account> getAccounts() throws IOException {
        return Arrays.asList(new ObjectMapper().readValue(new File(PATH), Account[].class));
    }

    public static void writeAccounts(List<Account> accounts) {
        ObjectMapper om = new ObjectMapper();

        try (FileWriter writer = new FileWriter(PATH, false)) {
            writer.append(om.writeValueAsString(accounts));
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static String getPATH() {
        return PATH;
    }

    public static void setPATH(String PATH) {
        FileHelper.PATH = PATH;
    }
}