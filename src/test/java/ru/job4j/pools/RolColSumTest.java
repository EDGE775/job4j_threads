package ru.job4j.pools;

import org.junit.Test;

import static org.junit.Assert.*;

public class RolColSumTest {

    @Test
    public void whenStandardSum() {
        int[][] array = {
                {1, 2},
                {4, 5},
        };
        RolColSum.Sums[] exp = {
                new RolColSum.Sums(3, 5),
                new RolColSum.Sums(9, 7)
        };
        assertArrayEquals(RolColSum.sum(array), exp);
    }

    @Test
    public void whenAsyncSum() {
        int[][] array = {
                {1, 2},
                {4, 5},
        };
        RolColSum.Sums[] exp = {
                new RolColSum.Sums(3, 5),
                new RolColSum.Sums(9, 7)
        };
        assertArrayEquals(RolColSum.sum(array), exp);
    }
}