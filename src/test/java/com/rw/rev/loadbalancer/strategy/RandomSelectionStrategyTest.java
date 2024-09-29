package com.rw.rev.loadbalancer.strategy;

import com.rw.rev.loadbalancer.Service;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RandomSelectionStrategyTest {

    @Test
    void testSelection() {
        // no mockito

        // arrange
        RandomSelectionStrategy selectionStrategy = new RandomSelectionStrategy(new SequentialNotRandom());
        List<Service> activeServices = List.of(new Service("serv1"), new Service("serv2"), new Service("serv3"));

        // act and assert
        assertEquals("serv1", selectionStrategy.select(activeServices).address());
        assertEquals("serv2", selectionStrategy.select(activeServices).address());
        assertEquals("serv3", selectionStrategy.select(activeServices).address());
    }

    private static class SequentialNotRandom extends Random {
        private int counter = -1;

        @Override
        public int nextInt(int bound) {
            counter++;
            return counter;
        }
    }
}
