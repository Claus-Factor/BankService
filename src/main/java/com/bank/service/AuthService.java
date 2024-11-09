// AuthService.java
package com.bank.service;

import com.bank.model.User;

import java.util.HashMap;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

public class AuthService {
    Map<String, User> users = new HashMap<>();

    Long userCounter = 1L;

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
            System.out.printf("Пользователь %s (%s) успешно зарегистрирован%n", name, email);
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
            if (currentUser == null)
                throw new Exception("Такого пользователя не существует");
            if (!verifyPassword(password, currentUser.getPasswordHash()))
                throw new Exception("Пароль не подходит");
            System.out.println("Добро пожаловать, " + currentUser.getName() + "!");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return currentUser;

    }

}