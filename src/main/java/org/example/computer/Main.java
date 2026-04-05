package org.example.computer;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Distributed Cache System Demo ===\n");

        Database database = new Database();

        database.put("user:1", "Alice");
        database.put("user:2", "Bob");
        database.put("user:3", "Charlie");
        database.put("product:100", "Laptop");
        database.put("product:200", "Mouse");

        DistributionStrategy strategy = new ModuloDistributionStrategy();
        DistributedCache cache = new DistributedCache(strategy, database);

        cache.addNode(new CacheNode("Node-A", 2));
        cache.addNode(new CacheNode("Node-B", 2));
        cache.addNode(new CacheNode("Node-C", 2));

        System.out.println("\n--- Testing GET operations (initial cache misses) ---");
        System.out.println("Get user:1: " + cache.get("user:1"));
        System.out.println("Get user:2: " + cache.get("user:2"));
        System.out.println("Get user:3: " + cache.get("user:3"));

        cache.printCacheStatus();

        System.out.println("\n--- Testing GET operations (should be cache hits) ---");
        System.out.println("Get user:1 again: " + cache.get("user:1"));
        System.out.println("Get user:2 again: " + cache.get("user:2"));

        System.out.println("\n--- Testing PUT operations ---");
        cache.put("user:4", "David");
        cache.put("product:300", "Keyboard");


        System.out.println("\n--- Testing LRU Eviction ---");
        System.out.println("Adding more items to trigger eviction...");
        cache.put("user:5", "Eve");
        cache.put("user:6", "Frank");
        cache.put("session:abc", "SessionData");

        cache.printCacheStatus();


        System.out.println("\n--- Testing after eviction ---");
        System.out.println("Get user:1 (might be evicted): " + cache.get("user:1"));


        System.out.println("\n--- Distribution Demonstration ---");
        String[] testKeys = {"alpha", "beta", "gamma", "delta", "epsilon"};
        for (String key : testKeys) {
            database.put(key, "Value for " + key);
            cache.get(key);
        }

        cache.printCacheStatus();

        System.out.println("\n=== Demo Complete ===");
        System.out.println("\nKey Design Features:");
        System.out.println("1. Pluggable Distribution Strategy (currently modulo)");
        System.out.println("2. Pluggable Eviction Policy (currently LRU)");
        System.out.println("3. Cache miss handled by fetching from database");
        System.out.println("4. Clean separation of concerns with interfaces");
        System.out.println("5. Easy to extend with new strategies/policies");
    }
}