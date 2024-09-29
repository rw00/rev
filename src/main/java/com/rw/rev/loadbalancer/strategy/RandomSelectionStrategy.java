package com.rw.rev.loadbalancer.strategy;

import com.rw.rev.loadbalancer.Service;
import java.util.List;
import java.util.Random;

public class RandomSelectionStrategy implements SelectionStrategy<Service> {
    private final Random random;

    public RandomSelectionStrategy() {
        this(new Random());
    }

    // Visible for testing only
    RandomSelectionStrategy(Random random) {
        this.random = random;
    }

    @Override
    public Service select(List<Service> list) {
        return list.get(random.nextInt(list.size()));
    }
}
