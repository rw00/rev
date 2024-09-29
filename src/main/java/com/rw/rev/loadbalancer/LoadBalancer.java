package com.rw.rev.loadbalancer;

public class LoadBalancer {
    private final Registry registry;

    public LoadBalancer(Registry registry) {
        this.registry = registry;
    }

    public String select() {
        return registry.selectAvailableService();
    }
}
