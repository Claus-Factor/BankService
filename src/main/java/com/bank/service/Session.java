// Session.java
package com.bank.service;

import com.bank.model.User;

public class Session {
    private static User currentUser = null;

    private static int unsuccessfulAttempts = 0; // Количество попыток неудачного входа во время текущей сессиии

    public static void login(User user) {
        unsuccessfulAttempts = 0;
        currentUser = user;
        System.out.println("Добро пожаловать, " + currentUser.getName() + "!");
    }

    public static void logout() {
        try {
            if (currentUser == null)
                throw new Exception("Нет активной сессии для выхода");
            System.out.println("Пользователь " + currentUser.getName() + " вышел из системы");
            currentUser = null;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getUnsuccessfulAttempts() {
        return unsuccessfulAttempts;
    }

    public static void increase() {
        unsuccessfulAttempts++;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isActive() {
        return currentUser != null;
    }
}
