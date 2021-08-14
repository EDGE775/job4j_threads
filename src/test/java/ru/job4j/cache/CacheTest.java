package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAddDifferent() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        Base base2 = new Base(2, 0);
        cache.add(base1);
        cache.add(base2);
        assertThat(cache.getById(1), is(base1));
        assertThat(cache.getById(2), is(base2));
    }

    @Test
    public void whenAddEqualIdThenNotResult() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        assertFalse(cache.add(new Base(1, 1)));
        assertThat(cache.getById(1), is(base));
    }

    @Test
    public void whenUpdateThenVersionIncrease() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        cache.update(new Base(1, 0));
        assertThat(cache.getById(1).getId(), is(1));
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateDifferentVersionsThenException() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        cache.update(new Base(1, 0));
        cache.update(new Base(1, 0));
    }

    @Test
    public void whenDeleteThenReturnNull() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 0);
        cache.add(base1);
        cache.delete(new Base(1, 0));
        assertNull(cache.getById(1));
    }
}