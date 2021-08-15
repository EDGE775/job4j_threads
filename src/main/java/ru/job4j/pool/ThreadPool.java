package ru.job4j.pool;

import ru.job4j.CASCount;
import ru.job4j.buffer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool(int queueSize) {
        int size = Runtime.getRuntime().availableProcessors();
        System.out.printf("Количество нитей: %d%n", size);
        tasks = new SimpleBlockingQueue<>(queueSize);
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        tasks.poll().run();
                        System.out.printf("Отработала нить: %s%n", Thread.currentThread().getName());
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.printf("Нить: %s завершила работу%n", Thread.currentThread().getName());
                }
            });
            thread.start();
            System.out.printf("Запущена нить: %s%n", thread.getName());
            threads.add(thread);
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
        System.out.printf("Задача %s передана в работу%n", job);
        for (Thread thread : threads) {
            if (thread.getState() == Thread.State.TIMED_WAITING) {
                thread.notify();
            }
        }
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
            System.out.printf("Нити %s передан сигнал на отключение%n", thread.getName());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(4);
        CASCount count = new CASCount();
        for (int i = 0; i < 100; i++) {
            threadPool.work(() -> count.increment());
        }
        Thread.sleep(1000);
        threadPool.shutdown();
        System.out.printf("Итоговый результат: %d%n", count.get());
    }
}