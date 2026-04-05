package org.example.computer;

import java.util.Map;

public interface EvictionPolicy<K, V> {
    void onKeyAccessed(K key);
    void onKeyAdded(K key);
    void onKeyRemoved(K key);
    K getKeyToEvict();
    Map<K, V> getCache();
}