package ru.job4j.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParallelSearchIndex<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final int from;
    private final int to;
    private final T obj;
    private final int maxArrayLength = 10;

    public ParallelSearchIndex(T[] array, T obj, int from, int to) {
        this.array = array;
        this.obj = obj;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        int size = to - from + 1;
        if (size <= maxArrayLength) {
            return lineFind();
        }
        int mid = (from + to) / 2;
        ParallelSearchIndex<T> leftSearch = new ParallelSearchIndex<>(array, obj, from, mid);
        ParallelSearchIndex<T> rightSearch = new ParallelSearchIndex<>(array, obj, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        Integer left = leftSearch.join();
        Integer right = rightSearch.join();
        return Math.max(left, right);
    }

    private int lineFind() {
        int result = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(obj)) {
                result = i;
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Integer[] array = new Integer[]{1, 3, 5, 7, 23, 23, 65, 34, 74, 5, 79, 6, 4, 56, 34, 5, 345, 2, 35, 37, 45, 75, 8, 6};
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ParallelSearchIndex<Integer> parallelSearchIndex
                = new ParallelSearchIndex<>(array, 79, 0, array.length - 1);
        System.out.println(forkJoinPool.invoke(parallelSearchIndex));
    }
}
