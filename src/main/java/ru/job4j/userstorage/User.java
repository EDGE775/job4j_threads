package ru.job4j.userstorage;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class User {
    private final int id;

    private volatile int amount;

    public User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public synchronized int getAmount() {
        return amount;
    }

    public synchronized void setAmount(int amount) {
        this.amount = amount;
    }

    public synchronized int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", amount=" + amount + '}';
    }
}
