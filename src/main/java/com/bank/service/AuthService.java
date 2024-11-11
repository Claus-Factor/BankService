// AuthService.java
package com.bank.service;

import com.bank.model.Account;
import com.bank.model.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

public class AuthService {
    private Map<String, User> users = new HashMap<>();

    private Long userCounter = 1L;

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }
    private boolean verifyPassword(String password, String passwordHash) {
        return BCrypt.checkpw(password, passwordHash);
    }

    // Метод регистрации пользователя банка
    public boolean register(String name, String email, String telephone, String password) {
        try {
            if (users.containsKey(email))
                throw new Exception("Указанный адрес электронной почты занят");
            String passwordHash = hashPassword(password);
            User newUser = new User(userCounter, name, email, telephone, passwordHash);
            users.put(email, newUser);
            userCounter++;
            System.out.printf("Пользователь %s(%s) успешно зарегистрирован%n", name, email);
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Метод авторизации пользователя банка
    public User login(String email, String password) {
        User currentUser = users.get(email);
        try {
            if (Session.isActive())
                throw new Exception("Текущая сессия занята");
            if (currentUser == null)
                throw new Exception("Такого пользователя не существует");
            if (Blocker.blockedUsers.containsKey(email)) {
                if (Blocker.blockedUsers.get(email).isAfter(LocalDateTime.now()))
                    throw new Exception("Пользователь " + currentUser.getName() + "(" + currentUser.getEmail() + ") временно заблокирован." +
                            "Осталось: " + Blocker.getUnit().between(LocalDateTime.now(),Blocker.blockedUsers.get(email)) + " секунд.");
                else
                    Blocker.restore(currentUser);
            }
            if (!verifyPassword(password, currentUser.getPasswordHash())) {
                Session.increase();
                if (Session.getUnsuccessfulAttempts() == 3) {
                    Blocker.block(currentUser);
                    throw new Exception("3 неверных попытки: пользователь " + currentUser.getName() + "(" + currentUser.getEmail() + ") временно заблокирован...");
                }
                throw new Exception("Пароль не подходит");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        Session.login(currentUser);
        return currentUser;
    }

    // Метод изменения пароля
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        User currentUser = users.get(email);
        try {
            if (currentUser == null)
                throw new Exception("Такого пользователя не существует");
            if (!verifyPassword(oldPassword, currentUser.getPasswordHash()))
                throw new Exception("Неверный старый пароль");
            currentUser.setPasswordHash(hashPassword(newPassword));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean resetPassword(String email, String newPassword) {
       try {
           if (!Session.isActive()) {
               throw new Exception("Нет активной сессии для сброса пароля");
           }
           if (Session.getCurrentUser() == null)
               throw new Exception("Такого пользователя не существует");
           if (!Session.getCurrentUser().equals(getUserByEmail(email)))
               throw new Exception("Данная учетная запись вам не принадлежит");
           Session.getCurrentUser().setPasswordHash(hashPassword(newPassword));
           System.out.println("Пароль успешно изменен.");
           return true;
       }
       catch (Exception e) {
           System.out.println(e.getMessage());
           return false;
       }
    }

    public User getUserByEmail(String email) {
        return users.get(email);
    }

}