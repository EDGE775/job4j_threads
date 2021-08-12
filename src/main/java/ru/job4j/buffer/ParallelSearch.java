package ru.job4j.buffer;

import java.util.concurrent.atomic.AtomicBoolean;

public class ParallelSearch {

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        AtomicBoolean isFinish = new AtomicBoolean(false);
        final Thread consumer = new Thread(
                () -> {
                    while (!isFinish.get()) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    isFinish.set(true);
                    consumer.interrupt();
                }
        ).start();
    }
}