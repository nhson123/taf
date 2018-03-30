package com.anecon.taf.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * This class is meant to store unused objects like clients so that they can be retrieved later on.
 *
 * @param <T> the type of objects that are stored in this pool
 */
public class ObjectPool<T> {
    private final List<T> pool = new ArrayList<>();

    /**
     * Add an unused object to the pool.
     * <p>
     * The object has to be in a clean and valid state before adding it to the pool!
     *
     * @param o the object to add
     */
    public void add(T o) {
        pool.add(Objects.requireNonNull(o));
    }

    /**
     * Retrieve a stored instance and remove it from the pool.
     *
     * @return an {@link Optional} of a stored object, or {@code Optional.empty()} if there are no available instances.
     */
    public Optional<T> pull() {
        if (pool.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(pool.remove(0));
        }
    }

    /**
     * Retrieve a stored instance that matches {@code predicate} and remove it from the pool.
     *
     * @return an {@link Optional} of a stored, matching object, or {@code Optional.empty()} if there are no available instances.
     */
    public Optional<T> pull(Predicate<? super T> predicate) {
        if (pool.size() == 0) {
            return Optional.empty();
        } else {
            final Optional<T> optional = pool.stream().filter(predicate).findFirst();

            optional.ifPresent(pool::remove);

            return optional;
        }
    }
}
