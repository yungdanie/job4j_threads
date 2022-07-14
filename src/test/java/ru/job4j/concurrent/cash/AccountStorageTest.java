package ru.job4j.concurrent.cash;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class AccountStorageTest {

    @Test
    void whenAdd() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertEquals(firstAccount.amount(), 100);
    }

    @Test
    void whenUpdate() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.update(new Account(1, 200));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertEquals(firstAccount.amount(), 200);
    }

    @Test
    void whenDelete() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.delete(1);
        assertTrue(storage.getById(1).isEmpty());
    }

    @Test
    void whenTransfer() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.add(new Account(2, 100));
        storage.transfer(1, 2, 100);
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        var secondAccount = storage.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 2"));
        assertEquals(firstAccount.amount(), 0);
        assertEquals(secondAccount.amount(), 200);
    }

    @Test
    void whenTransferAndSenderHasNotEnoughMoney() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 0));
        storage.add(new Account(2, 300));
        assertFalse(storage.transfer(1, 2, 100));
    }
}