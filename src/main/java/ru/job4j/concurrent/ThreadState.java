package ru.job4j.concurrent;

import static java.lang.Thread.State.TERMINATED;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName()));
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName()));
        System.out.println(first.getState());
        System.out.println(second.getState());
        first.start();
        second.start();
        System.out.println(first.getState());
        System.out.println(second.getState());
        while (first.getState() != TERMINATED || second.getState() != TERMINATED) {
            System.out.println(first.getState() + " " + second.getState());
        }
        System.out.printf("Работа нитей: %s и %s завершена!%n",
                first.getName(), second.getName());

    }
}
