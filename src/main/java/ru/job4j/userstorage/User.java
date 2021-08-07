package ru.job4j.userstorage;

import net.jcip.annotations.ThreadSafe;

public class User {
    private final int id;

    private volatile int amount;

    public User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", amount=" + amount + '}';
    }
}
