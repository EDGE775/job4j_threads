package ru.job4j.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<>();

    public synchronized boolean add(User user) {
        boolean result = false;
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            result = true;
        }
        return result;
    }

    public synchronized boolean update(User user) {
        boolean result = false;
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            result = true;
        }
        return result;
    }

    public synchronized boolean delete(User user) {
        boolean result = false;
        if (users.containsKey(user.getId())) {
            users.remove(user.getId(), user);
            result = true;
        }
        return result;
    }

    public synchronized void transfer(int fromId, int told, int amount) {
        if (users.containsKey(fromId) && users.containsKey(told)) {
            User fromUser = users.get(fromId);
            User toUser = users.get(told);
            if (fromUser.getAmount() < amount) {
                throw new IllegalArgumentException(
                        String.format("У юзера: %s недостаточно денег для перевода", fromUser));
            }
            fromUser.setAmount(fromUser.getAmount() - amount);
            toUser.setAmount(toUser.getAmount() + amount);
        }
    }

    public synchronized User getUserById(int id) {
        return users.get(id);
    }

    public static void main(String[] args) {
        UserStorage storage = new UserStorage();

        storage.add(new User(1, 100));
        storage.add(new User(2, 200));

        storage.transfer(1, 2, 50);

        System.out.println(storage.getUserById(1));
        System.out.println(storage.getUserById(2));
    }
}
