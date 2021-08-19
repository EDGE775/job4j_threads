package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ParallelSearchIndexTest {
    @Test
    public void whenFirstElement() {
        Integer[] array = new Integer[]{1, 3, 5};
        Integer obj = 1;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ParallelSearchIndex<Integer> searcher = new ParallelSearchIndex<>(
                array, obj, 0, array.length - 1);
        assertThat(forkJoinPool.invoke(searcher), is(0));
    }

    @Test
    public void whenLastElement() {
        Integer[] array = new Integer[]{1, 3, 5};
        Integer obj = 5;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ParallelSearchIndex<Integer> searcher = new ParallelSearchIndex<>(
                array, obj, 0, array.length - 1);
        assertThat(forkJoinPool.invoke(searcher), is(2));
    }

    @Test
    public void whenNotElement() {
        Integer[] array = new Integer[]{1, 3, 5};
        Integer obj = 7;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ParallelSearchIndex<Integer> searcher = new ParallelSearchIndex<>(
                array, obj, 0, array.length - 1);
        assertThat(forkJoinPool.invoke(searcher), is(-1));
    }
}