package org.example.computer;

import org.example.computer.CacheNode;

import java.util.List;


public interface DistributionStrategy {
    CacheNode getNodeForKey(String key, List<CacheNode> nodes);
    void addNode(CacheNode node);
    void removeNode(CacheNode node);
}