// Transaction.java
package com.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    public enum TransactionType {
        DEPOSIT, WITHDRAW, TRANSFER
    }

    private Long id;
    private LocalDateTime datetime;
    private Account fromAccount;
    private Account toAccount;
    private BigDecimal amount;
    private TransactionType type;


    public Transaction(Long id, LocalDateTime datetime, Account fromAccount, Account toAccount, BigDecimal amount, TransactionType type) {
        this.id = id;
        this.datetime = datetime;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.type = type;
    }

    public Transaction() {

    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", datetime=" + datetime +
                ", fromAccount=" + fromAccount +
                ", toAccount=" + toAccount +
                ", type=" + type +
                ", amount=" + amount +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
