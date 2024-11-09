package com.bank;

import com.bank.model.Account;
import com.bank.model.User;
import com.bank.service.AccountService;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        User user = new User(145L, "Николай", "volxin.k@yandex.ru", "+7(904)683-13-80", "sdfsd");
        Account account = new Account(226L, user, BigDecimal.valueOf(150));
        System.out.println(account);

        AccountService accountService = new AccountService();
        accountService.deposit(account, BigDecimal.valueOf(21));

        User user1 = new User(1L, "Alice", "alice@example.com", "+7(904)683-13-80","sdf");
        User user2 = new User(2L, "Bob", "bob@example.com", "+7(904)683-13-80", "sdfsd");

        Account account1 = new Account(1L, user1, BigDecimal.valueOf(1000));
        Account account2 = new Account(2L, user2, BigDecimal.valueOf(500));

        AccountService service = new AccountService();
        service.deposit(account1, BigDecimal.valueOf(200));
        service.withdraw(account2, BigDecimal.valueOf(100));
        service.transfer(account1, account2, BigDecimal.valueOf(300));

        System.out.println("Баланс аккаунта 1: " + account1.getBalance());
        System.out.println("Баланс аккаунта 2: " + account2.getBalance());


        service.printTransactionsHistory();
    }
}