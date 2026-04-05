package org.example.computer;

public class CacheNode {
    private final String nodeId;
    private final EvictionPolicy<String, Object> evictionPolicy;
    private final int capacity;

    public CacheNode(String nodeId, int capacity) {
        this.nodeId = nodeId;
        this.capacity = capacity;
        this.evictionPolicy = new LRUEvictionPolicy<>(capacity);
    }

    public Object get(String key) {
        if (evictionPolicy.getCache().containsKey(key)) {
            evictionPolicy.onKeyAccessed(key);
            return evictionPolicy.getCache().get(key);
        }
        return null;
    }

    public void put(String key, Object value) {

        if (evictionPolicy.getCache().size() >= capacity &&
                !evictionPolicy.getCache().containsKey(key)) {
            evictIfNeeded();
        }

        evictionPolicy.getCache().put(key, value);
        evictionPolicy.onKeyAdded(key);
    }

    private void evictIfNeeded() {
        if (evictionPolicy.getCache().size() >= capacity) {
            K keyToEvict = evictionPolicy.getKeyToEvict();
            if (keyToEvict != null) {
                evictionPolicy.onKeyRemoved(keyToEvict);
            }
        }
    }

    public boolean containsKey(String key) {
        return evictionPolicy.getCache().containsKey(key);
    }

    public String getNodeId() {
        return nodeId;
    }

    public int getCurrentSize() {
        return evictionPolicy.getCache().size();
    }

    public int getCapacity() {
        return capacity;
    }
}