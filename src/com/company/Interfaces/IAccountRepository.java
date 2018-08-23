package com.company.Interfaces;

import com.company.dto.Account;
import com.sun.istack.internal.Nullable;

import java.io.IOException;
import java.util.List;

public interface IAccountRepository{
    @Nullable
    Account getById(long id) throws IOException;
    void addAccount(Account account) throws IOException;
    void updateAccount(Account account) throws IOException;
    String getAccountInfoById(long accountId) throws IOException;
    List<Account> getAllAccounts() throws IOException;
    Account getCorrespondent() throws IOException;
    void removeAccountById(long accountid) throws IOException;
}