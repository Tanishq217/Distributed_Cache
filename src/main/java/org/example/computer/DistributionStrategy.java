package com.example.cache;

import java.util.List;


public interface DistributionStrategy {
    CacheNode getNodeForKey(String key, List<CacheNode> nodes);
    void addNode(CacheNode node);
    void removeNode(CacheNode node);
}