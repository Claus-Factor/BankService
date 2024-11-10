// Blocker.java
package com.bank.service;

import com.bank.model.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class Blocker {
    private static final ChronoUnit unit = ChronoUnit.SECONDS;
    public static Map<String, LocalDateTime> blockedUsers = new HashMap<>();

    public static void block(User user) {
        blockedUsers.put(user.getEmail(), LocalDateTime.now().plus(20, unit)); // блокировка на последующие 5 часов
    }
    public static void restore(User user) {
        blockedUsers.remove(user.getEmail());
    }

    public static ChronoUnit getUnit() {
        return unit;
    }
}