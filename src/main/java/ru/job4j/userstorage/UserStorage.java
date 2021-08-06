package ru.job4j.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.List;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final List<User> users = new LinkedList<>();

    public synchronized boolean add(User user) {
        boolean result = false;
        if (!isUserPresent(getIndex(user.getId()))) {
            users.add(user);
            result = true;
        }
        return result;
    }

    public synchronized boolean update(User user) {
        boolean result = false;
        int index = getIndex(user.getId());
        if (isUserPresent(index)) {
            users.set(index, user);
            result = true;
        }
        return result;
    }

    public synchronized boolean delete(User user) {
        boolean result = false;
        int index = getIndex(user.getId());
        if (isUserPresent(index)) {
            users.remove(index);
            result = true;
        }
        return result;
    }

    public synchronized void transfer(int fromId, int told, int amount) {
        int fromIndex = getIndex(fromId);
        int toIndex = getIndex(told);
        if (isUserPresent(fromIndex) && isUserPresent(toIndex)) {
            User fromUser = users.get(fromIndex);
            User toUser = users.get(toIndex);
            if (fromUser.getAmount() < amount) {
                throw new IllegalArgumentException(
                        String.format("У юзера: %s недостаточно денег для перевода", fromUser));
            }
            fromUser.setAmount(fromUser.getAmount() - amount);
            toUser.setAmount(toUser.getAmount() + amount);
        }
    }

    private synchronized int getIndex(int id) {
        int index = -1;
        for (int i = 0; i < users.size(); i++) {
            int userId = users.get(i).getId();
            if (userId == id) {
                index = i;
                break;
            }
        }
        return index;
    }

    private boolean isUserPresent(int userId) {
        return userId != -1;
    }

    public synchronized User getUserById(int id) {
        User result = null;
        int index = getIndex(id);
        if (isUserPresent(index)) {
            result = users.get(index);
        }
        return result;
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
