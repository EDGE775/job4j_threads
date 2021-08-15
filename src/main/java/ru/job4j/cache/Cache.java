package ru.job4j.cache;

import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (id, stored) -> {
            if (stored.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base result = new Base(model.getId(), model.getVersion() + 1);
            result.setName(model.getName());
            return result;
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public Base getById(int id) {
        return memory.get(id);
    }
}