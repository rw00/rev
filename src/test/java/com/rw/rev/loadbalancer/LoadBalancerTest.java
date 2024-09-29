package com.rw.rev.loadbalancer;

import com.rw.rev.loadbalancer.strategy.RandomSelectionStrategy;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoadBalancerTest {
    @Test
    void testBasicCase() {
        // arrange
        Service service1 = new Service("rev1");
        Registry registry = new Registry();
        registry.register(service1);

        LoadBalancer loadBalancer = new LoadBalancer(registry);

        // act
        String service = loadBalancer.select();

        // assert
        assertEquals("rev1", service);
    }

    @Test
    void testCannotRegisterSameServiceMoreThanOnce() {
        // arrange
        Service service = new Service("rev1");
        Registry registry = new Registry();
        registry.register(service);
        registry.register(new Service("rev1"));

        // act
        Set<Service> availableServices = registry.getAvailableServices();

        // assert
        assertEquals(1, availableServices.size());
        assertEquals(Set.of(service), availableServices);
    }

    @Test
    void testCannotRegisterMoreThanXServices() {
        Service service1 = new Service("rev-serv-1");
        Service service2 = new Service("rev-serv-2");
        Service service3 = new Service("rev-serv-3");

        Registry registry = new Registry(new RegistryConfiguration(3), new RandomSelectionStrategy());
        registry.register(service1);
        registry.register(service2);
        registry.register(service3);

        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                                                               () -> registry.register(new Service("rev1-serv-4")));
        assertEquals("Cannot register more than 3 services", exception.getMessage());
    }
}
