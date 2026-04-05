package org.example.computer;

import java.util.*;

public class LRUEvictionPolicy<K, V> implements EvictionPolicy<K, V> {
    private final int capacity;


    private final Map<K, V> cache;

    private final LinkedHashMap<K, Long> accessOrder;

    public LRUEvictionPolicy(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.accessOrder = new LinkedHashMap<>();
    }

    @Override
    public void onKeyAccessed(K key) {

        if (accessOrder.containsKey(key)) {

            accessOrder.remove(key);
        }
        accessOrder.put(key, System.nanoTime());

    }

    @Override
    public void onKeyAdded(K key) {

        accessOrder.put(key, System.nanoTime());
    }


    @Override


    public void onKeyRemoved(K key) {
        accessOrder.remove(key);

        cache.remove(key);
    }

    @Override

    public K getKeyToEvict() {

        if (accessOrder.isEmpty()) return null;
        return accessOrder.keySet().iterator().next();
    }

    @Override
    public Map<K, V> getCache() {
        return cache;
    }

    public int getCapacity() {
        return capacity;
    }
}