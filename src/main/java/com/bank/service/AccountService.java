// AccountService.java
package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountService {
    private List<Transaction> transactionsHistory = new ArrayList<>();
    private Map<Long, Account> accounts = new HashMap<>();
    private Long accountCounter = 1L;

    private Account getAccountById(Long id) {
        return accounts.get(id);
    }

    public HashMap<Integer, Account> currentUserAccounts() {
        HashMap<Integer, Account> currentUserAccounts = new HashMap<>();
        for (int key = 1; key <= accountCounter; key++) {
            if (accounts.get(key + 10L*7).getUser().equals(Session.getCurrentUser())) {
                currentUserAccounts.put(key, accounts.get(key * 10L*7));
            }
        }
        return currentUserAccounts;
    }

    public boolean createAccount() {
        try {
            if (!Session.isActive())
                throw new Exception("Нет активной сессии для открытия счета");
            Long id = 10L*7 + accountCounter;
            Account newAcccount = new Account(id, Session.getCurrentUser(), BigDecimal.valueOf(0));
            accounts.put(id, newAcccount);
            System.out.println("Клиент " + Session.getCurrentUser().getName() + "(" + Session.getCurrentUser().getEmail() + ")" +
                    "создал новый счёт");
            accountCounter++;

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void deposit(Account account, BigDecimal amount) {
        try {
            if (!Session.isActive())
                throw new Exception("Нет активной сессии для осуществления транзакции");
            if (!Session.getCurrentUser().equals(account.getUser()))
                throw new Exception("Выбранный счет принадлежит другому клиенту банка");
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
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void withdraw(Account account, BigDecimal amount) {
        try {
            if (!Session.isActive())
                throw new Exception("Нет активной сессии для осуществления транзакции");
            if (!Session.getCurrentUser().equals(account.getUser()))
                throw new Exception("Выбранный счет принадлежит другому клиенту банка");
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

    public void transfer(Account fromAccount, Long toAccountId, BigDecimal amount) {
        try {
            if (!Session.isActive())
                throw new Exception("Нет активной сессии для осуществления транзакции");
            if (!Session.getCurrentUser().equals(fromAccount.getUser()))
                throw new Exception("Выбранный счет принадлежит другому клиенту банка");
            if (fromAccount.getBalance().compareTo(amount) < 0)
                throw new Exception("Недостаточно средств");
            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));

            Account toAccount = getAccountById(toAccountId);
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

    public void printTransactionsHistory() {
        for (Transaction t : transactionsHistory)
            System.out.println(t);
    }

}