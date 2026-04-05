package org.example.computer;


import java.util.HashMap;
import java.util.Map;

public class Database {
    private final Map<String, Object> storage = new HashMap<>();

    public Object get(String key) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return storage.get(key);
    }

    public void put(String key, Object value) {
        storage.put(key, value);
        System.out.println("Database updated: " + key + " = " + value);
    }

    public boolean containsKey(String key) {
        return storage.containsKey(key);
    }
}