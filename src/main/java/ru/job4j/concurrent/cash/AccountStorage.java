package ru.job4j.concurrent.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {

    @GuardedBy("accounts")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        boolean res;
        synchronized (accounts) {
            res = accounts.putIfAbsent(
                    account.id(),
                    new Account(account.id(), account.amount())
            ) == null;
        }
        return res;
    }

    public boolean update(Account account) {
        boolean res = false;
        synchronized (accounts) {
            Optional<Account> foundAcc = getById(account.id());
            if (foundAcc.isPresent()) {
                Account prevAcc = accounts.put(account.id(), new Account(account.id(), account.amount()));
                res = prevAcc != null;
            }
        }
        return res;
    }

    public boolean delete(int id) {
        boolean res;
        synchronized (accounts) {
            res = accounts.remove(id) != null;
        }
        return res;
    }

    public Optional<Account> getById(int id) {
        Account sendAccount = null;
        synchronized (accounts) {
            Account account = accounts.get(id);
            if (account != null) {
                sendAccount = new Account(account.id(), account.amount());
            }
        }
        return Optional.ofNullable(sendAccount);
    }

    public boolean transfer(int fromId, int toId, int amount) {
        boolean res = false;
        synchronized (accounts) {
            Optional<Account> accountSender = getById(fromId);
            Optional<Account> accountRecipient = getById(toId);
            if (accountSender.isPresent() && accountRecipient.isPresent()) {
                Account sender = accountSender.get();
                Account recipient = accountRecipient.get();
                if (sender.amount() - amount >= 0) {
                    update(new Account(recipient.id(), recipient.amount() + amount));
                    update(new Account(sender.id(), sender.amount() - amount));
                    res = true;
                }
            }
        }
        return res;
    }
}
