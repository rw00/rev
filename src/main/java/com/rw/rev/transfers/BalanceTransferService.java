package com.rw.rev.transfers;

import java.math.BigDecimal;

class BalanceTransferService {

    // transactional
    void transfer(Account from, Account to, Amount amount) {
        if (from.id.equals(to.id)) {
            throw new IllegalArgumentException();
        }
        if (amount.quantity.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        // Deadlock Scenario:
        // transferA from account1 to account2
        // transferB from account2 to account1
        //  one thread acquires lock for account1 and waiting for account2
        //  second thread has already acquired lock for account2 and waiting for account1
        //  Deadlock!

        // in SQL DB:
        // optimistic locking on version with READ_COMMITTED

        // ```
        // SELECT balance, version FROM account WHERE id = $id
        // UPDATE account SET balance = balance - $amount, version = $version + 1 WHERE id = $id AND version = $version
        // ```

        // more info:

        // SERIALIZABLE as if it's sequential with retries

        // or SELECT FOR UPDATE:

        // ```
        // BEGIN
        // SELECT * FROM account WHERE id = $id FOR UPDATE
        // UPDATE account SET balance = balance - $amount WHERE id = $id
        // COMMIT
        // ```

        try {
            if (from.lock.tryLock() && to.lock.tryLock()) {
                if (from.amount.quantity.compareTo(amount.quantity) >= 0) {
                    to.amount.quantity = to.amount.quantity.add(amount.quantity);

                    from.amount.quantity = from.amount.quantity.subtract(amount.quantity);
                } else {
                    throw new IllegalStateException("Insufficient funds");
                }
            } else {
                throw new RetryableException();
            }
        } finally {
            from.lock.unlock();
            to.lock.unlock();
        }
    }
}
