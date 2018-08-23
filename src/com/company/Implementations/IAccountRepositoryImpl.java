package com.company.Implementations;

import com.company.Helpers.FileHelper;
import com.company.Interfaces.IAccountRepository;
import com.company.dto.Account;
import com.sun.istack.internal.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IAccountRepositoryImpl implements IAccountRepository {


    @Nullable
    @Override
    public Account getById(long id) throws IOException {
        return FileHelper.getAccounts().stream().filter(acc -> id == acc.getId()).findAny().orElse(null);
    }

    @Override
    public List<Account> getAllAccounts() throws IOException {
        return FileHelper.getAccounts();
    }

    @Override
    public Account getCorrespondent() throws IOException {
       return FileHelper.getAccounts().stream().filter(acc -> acc.isCorrespondent() == true).findAny().orElse(null);
    }

    @Override
    public void addAccount(Account account) throws IOException {
        List<Account> accounts = new ArrayList<>(FileHelper.getAccounts());
        Account finded = getById(account.getId());
        if (finded == null) {
            accounts.add(account);
        } else {
            throw new RuntimeException("Счет уже существует");
        }
        FileHelper.writeAccounts(accounts);
    }

    @Override
    public void updateAccount(Account account) throws IOException {
        List<Account> accounts = new ArrayList<>(FileHelper.getAccounts());
        Account finded = getById(account.getId());
        if (finded == null) {
            throw new RuntimeException("Счет не найден");
        } else {
            accounts.remove(finded);
            accounts.add(account);
        }
        FileHelper.writeAccounts(accounts);
    }

    @Override
    public String getAccountInfoById(long accountId) throws IOException {
        Account account = getById(accountId);
        if (account != null) {
            return account.toString();
        } else {
            return "Счет не найден";
        }
    }

    @Override
    public void removeAccountById(long accountid) throws IOException {
            List<Account> accounts = new ArrayList<>(FileHelper.getAccounts());
            Account removable = accounts.stream().filter(acc -> acc.getId() == accountid).findAny().orElse(null);
            if (removable != null){
                accounts.remove(removable);
            } else {
                throw new RuntimeException("Счет не найден");
            }
        FileHelper.writeAccounts(accounts);
    }
}
