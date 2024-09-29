package com.rw.rev.loadbalancer.strategy;

import com.rw.rev.loadbalancer.Service;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinSelectionStrategy implements SelectionStrategy<Service> {
    private final AtomicInteger index = new AtomicInteger(0);

    @Override
    public Service select(List<Service> list) {
        return list.get(index.getAndUpdate(current -> {
            if (current + 1 >= list.size()) {
                return 0;
            }
            return current + 1;
        }));
    }
}
