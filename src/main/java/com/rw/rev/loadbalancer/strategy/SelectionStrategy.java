package com.rw.rev.loadbalancer.strategy;

import java.util.List;

public interface SelectionStrategy<T> {
    /**
     * list should neither be null nor empty
     * Returns an instance based on the underlying algorithm.
     */
    T select(List<T> list);
}
