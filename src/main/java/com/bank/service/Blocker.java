// Blocker.java
package com.bank.service;

import com.bank.model.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class Blocker {
    private static final ChronoUnit unit = ChronoUnit.SECONDS;
    private static final long duration = 2000;
    public static Map<String, LocalDateTime> blockedUsers = new HashMap<>();

    public static void block(User user) {
        blockedUsers.put(user.getEmail(), LocalDateTime.now().plus(duration, unit)); // блокировка на последующие 2000 секунд
    }
    public static void restore(User user) {
        blockedUsers.remove(user.getEmail());
    }

    public static ChronoUnit getUnit() {
        return unit;
    }
}