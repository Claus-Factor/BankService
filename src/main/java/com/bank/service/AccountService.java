// AccountService.java
package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private final List<Transaction> transactionsHistory = new ArrayList<>();

    public void printTransactionsHistory() {
        for (Transaction t : transactionsHistory)
            System.out.println(t);
    }

    public void deposit(Account account, BigDecimal amount) {
        account.setBalance(account.getBalance().add(amount));
        Transaction transaction = new Transaction(
                transactionsHistory.size()+ 1L,
                LocalDateTime.now(),
                null,
                account,
                amount,
                Transaction.TransactionType.DEPOSIT
        );
        transactionsHistory.add(transaction);
        System.out.println("Внесена сумма депозита: +" + amount + " рублей");
    }

    public void withdraw(Account account, BigDecimal amount) {
        try {
            if (account.getBalance().compareTo(amount) < 0)
                throw new Exception("Недостаточно средств");
            account.setBalance(account.getBalance().subtract(amount));
            Transaction transaction = new Transaction(
                    transactionsHistory.size()+ 1L,
                    LocalDateTime.now(),
                    account,
                    null,
                    amount,
                    Transaction.TransactionType.WITHDRAW
            );
            transactionsHistory.add(transaction);
            System.out.println("Снятие наличных: -" + amount + " рублей");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void transfer(Account fromAccount, Account toAccount, BigDecimal amount) {
        try {
            if (fromAccount.getBalance().compareTo(amount) < 0)
                throw new Exception("Недостаточно средств");
            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            toAccount.setBalance(toAccount.getBalance().add(amount));
            Transaction transaction = new Transaction(
                    transactionsHistory.size()+ 1L,
                    LocalDateTime.now(),
                    fromAccount,
                    toAccount,
                    amount,
                    Transaction.TransactionType.TRANSFER
            );
            transactionsHistory.add(transaction);
            System.out.printf("Перевод средств от [%s] к [%s] в размере %s рублей%n",
                    fromAccount.getUser().getName(), toAccount.getUser().getName(), amount);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}