package ru.job4j;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenConsume5ThenProduce5() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        List<Integer> sourse = List.of(1, 2, 3, 4, 5);
        AtomicInteger index = new AtomicInteger();
        List<Integer> rsl = new ArrayList<>();
        Thread consumer = new Thread(
                () -> {
                    try {
                        while (rsl.size() < sourse.size()) {
                            Thread.sleep(100);
                            rsl.add(queue.poll());
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        Thread producer = new Thread(
                () -> {
                    try {
                        while (rsl.size() < sourse.size() - 1) {
                            Thread.sleep(100);
                            queue.offer(sourse.get(index.getAndIncrement()));
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        consumer.start();
        producer.start();
        consumer.join();
        producer.join();
        assertThat(rsl, is(sourse));
    }
}