package org.example.computer;


import java.util.List;

public class ModuloDistributionStrategy implements DistributionStrategy {

    private List<CacheNode> nodes;

    @Override
    public CacheNode getNodeForKey(String key, List<CacheNode> nodes) {
        this.nodes = nodes;

        if (nodes == null || nodes.isEmpty()) {

            return null;
        }

        int hashCode = Math.abs(key.hashCode());

        int nodeIndex = hashCode % nodes.size();
        return nodes.get(nodeIndex);
    }

    @Override
    public void addNode(CacheNode node) {


        System.out.println("Adding node would require rebalancing for modulo strategy");
    }

    @Override
    public void removeNode(CacheNode node) {
        System.out.println("Removing node would require rebalancing for modulo strategy");
    }
}