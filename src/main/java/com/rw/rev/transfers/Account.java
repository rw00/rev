package com.rw.rev.transfers;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Account {
    final String id;
    final Lock lock = new ReentrantLock();
    Amount amount;

    Account(String id) {
        this.id = id;
    }
}
