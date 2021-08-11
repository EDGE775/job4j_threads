package ru.job4j;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                    System.out.println("Ещё не готов!");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Продолжил работу!");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(3);
        Thread thread1 = new Thread(
                () -> {
                    try {
                        System.out.println(Thread.currentThread().getName() + " started");
                        Thread.sleep(100);
                        countBarrier.count();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
        Thread thread2 = new Thread(
                () -> {
                    try {
                        System.out.println(Thread.currentThread().getName() + " started");
                        Thread.sleep(100);
                        countBarrier.count();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
        Thread thread3 = new Thread(
                () -> {
                    try {
                        System.out.println(Thread.currentThread().getName() + " started");
                        Thread.sleep(100);
                        countBarrier.count();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
        thread1.start();
        thread2.start();
        thread3.start();
        countBarrier.await();
    }
}