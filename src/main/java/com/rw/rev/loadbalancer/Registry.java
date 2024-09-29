package com.rw.rev.loadbalancer;

import com.rw.rev.loadbalancer.strategy.RandomSelectionStrategy;
import com.rw.rev.loadbalancer.strategy.SelectionStrategy;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Registry {
    private final RegistryConfiguration registryConfig;
    private final SelectionStrategy<Service> selectionStrategy;
    private final List<Service> activeServices = new CopyOnWriteArrayList<>();
    private final Lock registrationLock = new ReentrantLock();

    public Registry(RegistryConfiguration registryConfig, SelectionStrategy<Service> selectionStrategy) {
        this.registryConfig = registryConfig;
        this.selectionStrategy = selectionStrategy;
    }

    // Visible for testing only
    Registry() {
        this(new RegistryConfiguration(10), new RandomSelectionStrategy());
    }

    /**
     * Returns true if the service was registered
     */
    public boolean register(Service service) {
        registrationLock.lock();
        try {
            if (activeServices.size() < registryConfig.maxNumberOfInstances()) {
                if (!activeServices.contains(service)) {
                    activeServices.add(service);
                    return true;
                }
                return false;
            }
        } finally {
            registrationLock.unlock();
        }
        throw new UnsupportedOperationException(String.format("Cannot register more than %d services",
                                                              registryConfig.maxNumberOfInstances()));
    }

    public String selectAvailableService() {
        if (activeServices.isEmpty()) {
            throw new IllegalStateException("No registered services");
        }
        return selectionStrategy.select(activeServices).address();
    }

    public Set<Service> getAvailableServices() {
        return Set.copyOf(activeServices);
    }
}
