package com.anecon.taf.core;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.ConcurrentInitializer;

import java.util.function.Supplier;

/**
 * A slightly adapted version of {@link org.apache.commons.lang3.concurrent.LazyInitializer} from apache commons.
 * Creating a lazy initializer is done using {@link Lazy#initializingWith(Supplier)}
 */
public class Lazy<T> implements ConcurrentInitializer<T> {
    /** Stores the managed object. */
    private volatile T object;
    private Supplier<T> initializer;

    private Lazy(Supplier<T> initializer) {
        this.initializer = initializer;
    }

    public static <T> Lazy<T> initializingWith(Supplier<T> initializer) {
        return new Lazy<>(initializer);
    }

    /**
     * Returns the object wrapped by this instance. On first access the object
     * is created. After that it is cached and can be accessed pretty fast.
     *
     * @return the object initialized by this {@code LazyInitializer}
     */
    @Override
    public T get() {
        // use a temporary variable to reduce the number of reads of the
        // volatile field
        T result = object;

        if (result == null) {
            synchronized (this) {
                result = object;
                if (result == null) {
                    object = result = initializer.get();
                }
            }
        }

        return result;
    }
}
