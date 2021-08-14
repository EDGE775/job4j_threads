package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        Integer currentValue;
        Integer nextValue;
        do {
            currentValue = count.get();
            nextValue = currentValue + 1;
        } while (!count.compareAndSet(currentValue, nextValue));
    }

    public int get() {
        return count.get();
    }
}