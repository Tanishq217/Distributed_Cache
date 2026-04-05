package org.example.computer;


import java.util.ArrayList;
import java.util.List;

public class DistributedCache {
    private final List<CacheNode> nodes;
    private final DistributionStrategy distributionStrategy;
    private final Database database;

    public DistributedCache(DistributionStrategy distributionStrategy, Database database) {
        this.nodes = new ArrayList<>();
        this.distributionStrategy = distributionStrategy;
        this.database = database;
    }

    public void addNode(CacheNode node) {
        nodes.add(node);
        distributionStrategy.addNode(node);
        System.out.println("Added node: " + node.getNodeId());
    }

    public void removeNode(CacheNode node) {
        nodes.remove(node);
        distributionStrategy.removeNode(node);
        System.out.println("Removed node: " + node.getNodeId());
    }

    public Object get(String key) {
        CacheNode targetNode = distributionStrategy.getNodeForKey(key, nodes);

        if (targetNode == null) {
            System.out.println("No nodes available!");
            return null;
        }

        Object value = targetNode.get(key);

        if (value != null) {
            System.out.println("Cache HIT for key: " + key + " on node: " + targetNode.getNodeId());
            return value;
        }

        System.out.println("Cache MISS for key: " + key + " - fetching from database");
        value = database.get(key);

        if (value != null) {
            targetNode.put(key, value);
            System.out.println("Stored in cache on node: " + targetNode.getNodeId());
        }

        return value;
    }

    public void put(String key, Object value) {
        database.put(key, value);


        CacheNode targetNode = distributionStrategy.getNodeForKey(key, nodes);

        if (targetNode != null) {
            targetNode.put(key, value);
            System.out.println("Stored in cache on node: " + targetNode.getNodeId());
        }
    }

    public void printCacheStatus() {
        System.out.println("\n=== Cache Status ===");
        for (CacheNode node : nodes) {
            System.out.println("Node " + node.getNodeId() + ": " +
                    node.getCurrentSize() + "/" + node.getCapacity() + " items");
        }
        System.out.println("===================\n");
    }
}