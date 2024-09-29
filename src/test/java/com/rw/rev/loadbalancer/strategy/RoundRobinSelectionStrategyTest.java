package com.rw.rev.loadbalancer.strategy;

import com.rw.rev.loadbalancer.Service;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RoundRobinSelectionStrategyTest {

    @Test
    void testBasic() {
        // arrange
        RoundRobinSelectionStrategy selectionStrategy = new RoundRobinSelectionStrategy();
        List<Service> activeServices = List.of(new Service("serv1"), new Service("serv2"), new Service("serv3"));

        // act and assert
        assertEquals("serv1", selectionStrategy.select(activeServices).address());
        assertEquals("serv2", selectionStrategy.select(activeServices).address());
        assertEquals("serv3", selectionStrategy.select(activeServices).address());
        assertEquals("serv1", selectionStrategy.select(activeServices).address());
        assertEquals("serv2", selectionStrategy.select(activeServices).address());
        assertEquals("serv3", selectionStrategy.select(activeServices).address());
        assertEquals("serv1", selectionStrategy.select(activeServices).address());
    }
}
