package com.company.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.internal.Nullable;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.util.Objects;

public class Account {
    private long id;
    private long balance;
    @Nullable
    private Long bankId;

    public Account(){}

    public Account(long id, long balance, Long bankId) {
        this.id = id;
        this.balance = balance;
        this.bankId = bankId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    @JsonIgnore
    public boolean isCorrespondent(){
        return this.bankId != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                balance == account.balance &&
                Objects.equals(bankId, account.bankId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance, bankId);
    }
}
