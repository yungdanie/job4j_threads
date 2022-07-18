package ru.job4j.concurrent.synch;

import ru.job4j.concurrent.cash.Account;

public class Accounts implements Cloneable {
    private int amount;

    public Accounts(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        clone = new Accounts(this.amount);
        return clone;
    }
}
