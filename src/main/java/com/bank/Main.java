package com.bank;

import com.bank.model.Account;
import com.bank.model.User;
import com.bank.service.AccountService;
import com.bank.service.AuthService;
import com.bank.service.Session;

import java.math.BigDecimal;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        AuthService authService = new AuthService(); // загружаем сервис регистрации клиента Банка
        AccountService accountService = new AccountService(); // загружаем сервис обслуживания
        User currentUser = null; // Пользователь текущей сессии

//        Account currentAccount = null; // Счет текущщего пользователя (потом реализуем возможность нескольких счетов для одного клиента банка)

        Scanner scanner = new Scanner(System.in);

        // Регистрация некоторых клиентов для примера
        authService.register("Nick", "nick@mail.com", "1234567890", "password1");
        authService.register("Jane", "jane@mail.com", "1234567890", "password2");
        authService.register("Sam", "sam@mail.com", "1234567890", "password3");
        authService.register("Peter", "peter@mail.com", "1234567890", "password4");
        authService.register("Alice", "alice@mail.com", "1234567890", "password5");

        boolean isConinued = true;
        while (isConinued) {
            System.out.println("""
                    
                     ************************
                    Выберите действие:
                    1. Регистрация нового клиента
                    2. Авторизация клиента банка
                    3. Открытие нового счета
                    4. Внесение депозита
                    5. Снятие средств
                    6. Перевод средств
                    7. Завершить текущую сессию
                    8. Выход из сервиса
                     ************************
                    """);

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.println("Введите: имя, эл.почта, телефон, пароль");
                    String name = scanner.next(); String email = scanner.next(); String tel = scanner.next(); String password = scanner.next();
                    authService.register(name, email, tel, password);
                }
                case 2 -> {
                    System.out.println("Введите: эл.почта, пароль");
                    String email = scanner.next(); String password = scanner.next();
                    currentUser = authService.login(email, password);
                }
                case 3 -> {
                    accountService.createAccount();
                    for (Account a : accountService.currentUserAccounts().values()) {
                        System.out.println(a);
                    }

                }
                case 4 -> {
                    System.out.println("Введите: сумма депозита, уникальный номер вашего счета");
                    String amount = scanner.next();
                    Long number = scanner.nextLong();
                    accountService.deposit(number, new BigDecimal(amount));

//                    /* Вариант с относительными номерами счетов клиента */
//                    System.out.println("Введите: сумма депозита, порядковый номер вашего счета");
//                    String amount = scanner.next();
//                    Integer number = scanner.nextInt();
//                    Long myAccountId = accountService.currentUserAccounts().get(number).getId();
//                    accountService.deposit(myAccountId, new BigDecimal(amount));
                }
                case 5 -> {
                    System.out.println("Введите: сумма снятия, уникальный номер вашего счета");
                    String amount = scanner.next();
                    Long number = scanner.nextLong();
                    accountService.withdraw(number, new BigDecimal(amount));
                }
                case 6 -> {
                    System.out.println("Введите: сумма перевода, номер вашего счета, номер счета получателя");
                    String amount = scanner.next();
                    Long myNumber = scanner.nextLong();
                    Long number = scanner.nextLong();
                    accountService.transfer(myNumber, number, new BigDecimal(amount));
                }
                case 7 -> {
                    Session.logout();
                }
                case 8 -> {
                    isConinued = false;
                }
                default -> System.out.println("Повторите попытку");
            }

        }

//        User alice = authService.login("alice@example.com", "password6");
//        alice = authService.login("alice@example.com", "password7");
//        alice = authService.login("alice@example.com", "password7");
//        alice = authService.login("alice@example.com", "password7");
//        for (int i = 0; i < 10; i++) {
//            String email = scanner.next();
//            String password = scanner.next();
//
//            alice = authService.login(email, password);
//        }
//
//        Account aliceAccount = new Account(1L, alice, BigDecimal.valueOf(1000));
//        accountService.deposit(aliceAccount, BigDecimal.valueOf(200));
//        accountService.withdraw(aliceAccount, BigDecimal.valueOf(100));

    }

}