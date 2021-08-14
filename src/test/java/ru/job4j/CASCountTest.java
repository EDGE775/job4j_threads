package ru.job4j;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void whenIncrement1Then1() {
        CASCount counter = new CASCount();
        counter.increment();
        assertThat(counter.get(), is(1));
    }

    @Test
    public void whenIncrement2Thread() throws InterruptedException {
        CASCount counter = new CASCount();
        Runnable runnable = () -> {
            for (int i = 0; i < 50; i++) {
                counter.increment();
            }
        };
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(counter.get(), is(100));
    }
}