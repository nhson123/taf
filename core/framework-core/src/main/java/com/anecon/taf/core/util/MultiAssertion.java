package com.anecon.taf.core.util;

import org.testng.Assert;

import java.util.*;

@SuppressWarnings("unused")
public class MultiAssertion {
    private final List<Runnable> assertions = new ArrayList<>();

    /**
     * This method will execute all previously added assertions, throwing {@link MultiAssertionError}  if one or more
     * assertions failed.
     *
     * @throws MultiAssertionError   if there was at least one failed assertion
     * @throws IllegalStateException if there were no assertions added before calling this method
     */
    public void performAssertions() {
        if (assertions.isEmpty()) {
            throw new IllegalStateException("You have to add assertions first!");
        }

        final List<Throwable> exceptions = new ArrayList<>(assertions.size());
        for (Runnable assertion : assertions) {
            try {
                assertion.run();
            } catch (Throwable t) {
                exceptions.add(t);
            }
        }

        if (!exceptions.isEmpty()) {
            throw new MultiAssertionError(exceptions);
        }
    }

    /**
     * @see Assert#assertEquals(Object, Object, String)
     */
    public MultiAssertion assertEquals(Object actual, Object expected, String message) {
        assertions.add(() -> Assert.assertEquals(actual, expected, message));
        return this;
    }

    /**
     * @see Assert#assertEquals(String, String, String)
     */
    public MultiAssertion assertEquals(String actual, String expected, String message) {
        assertions.add(() -> Assert.assertEquals(actual, expected, message));
        return this;
    }

    /**
     * @see Assert#assertEquals(double, double, double, String)
     */
    public MultiAssertion assertEquals(double actual, double expected, double delta, String message) {
        assertions.add(() -> Assert.assertEquals(actual, expected, delta, message));
        return this;
    }

    /**
     * @see Assert#assertEquals(float, float, float, String)
     */
    public MultiAssertion assertEquals(float actual, float expected, float delta, String message) {
        assertions.add(() -> Assert.assertEquals(actual, expected, delta, message));
        return this;
    }

    /**
     * @see Assert#assertEquals(long, long, String)
     */
    public MultiAssertion assertEquals(long actual, long expected, String message) {
        assertions.add(() -> Assert.assertEquals(actual, expected, message));
        return this;
    }

    /**
     * @see Assert#assertEquals(boolean, boolean, String)
     */
    public MultiAssertion assertEquals(boolean actual, boolean expected, String message) {
        assertions.add(() -> Assert.assertEquals(actual, expected, message));
        return this;
    }

    /**
     * @see Assert#assertEquals(byte, byte, String)
     */
    public MultiAssertion assertEquals(byte actual, byte expected, String message) {
        assertions.add(() -> Assert.assertEquals(actual, expected, message));
        return this;
    }

    /**
     * @see Assert#assertEquals(char, char, String)
     */
    public MultiAssertion assertEquals(char actual, char expected, String message) {
        assertions.add(() -> Assert.assertEquals(actual, expected, message));
        return this;
    }

    /**
     * @see Assert#assertEquals(short, short, String)
     */
    public MultiAssertion assertEquals(short actual, short expected, String message) {
        assertions.add(() -> Assert.assertEquals(actual, expected, message));
        return this;
    }

    /**
     * @see Assert#assertEquals(int, int, String)
     */
    public MultiAssertion assertEquals(int actual, int expected, String message) {
        assertions.add(() -> Assert.assertEquals(actual, expected, message));
        return this;
    }

    /**
     * @see Assert#assertEquals(Collection, Collection, String)
     */
    public MultiAssertion assertEquals(Collection<?> actual, Collection<?> expected, String message) {
        assertions.add(() -> Assert.assertEquals(actual, expected, message));
        return this;
    }

    /**
     * @see Assert#assertEquals(Iterator, Iterator, String)
     */
    public MultiAssertion assertEquals(Iterator<?> actual, Iterator<?> expected, String message) {
        assertions.add(() -> Assert.assertEquals(actual, expected, message));
        return this;
    }

    /**
     * @see Assert#assertEquals(Iterable, Iterable, String)
     */
    public MultiAssertion assertEquals(Iterable<?> actual, Iterable<?> expected, String message) {
        assertions.add(() -> Assert.assertEquals(actual, expected, message));
        return this;
    }

    /**
     * @see Assert#assertEquals(Object[], Object[], String)
     */
    public MultiAssertion assertEquals(Object[] actual, Object[] expected, String message) {
        assertions.add(() -> Assert.assertEquals(actual, expected, message));
        return this;
    }

    /**
     * @see Assert#assertEquals(byte[], byte[], String)
     */
    public MultiAssertion assertEquals(byte[] actual, byte[] expected, String message) {
        assertions.add(() -> Assert.assertEquals(actual, expected, message));
        return this;
    }

    /**
     * @see Assert#assertEquals(Set, Set, String)
     */
    public MultiAssertion assertEquals(Set<?> actual, Set<?> expected, String message) {
        assertions.add(() -> Assert.assertEquals(actual, expected, message));
        return this;
    }

    /**
     * @see Assert#assertEquals(Map, Map)
     */
    public MultiAssertion assertEquals(Map<?, ?> actual, Map<?, ?> expected) {
        assertions.add(() -> Assert.assertEquals(actual, expected));
        return this;
    }

    /**
     * @see Assert#assertEqualsNoOrder(Object[], Object[], String)
     */
    public MultiAssertion assertEqualsNoOrder(Object[] actual, Object[] expected, String message) {
        assertions.add(() -> Assert.assertEqualsNoOrder(actual, expected, message));
        return this;
    }

    /**
     * @see Assert#assertNotEquals(double, double, double, String)
     */
    public MultiAssertion assertNotEquals(double actual1, double actual2, double delta, String message) {
        assertions.add(() -> Assert.assertNotEquals(actual1, actual2, delta, message));
        return this;
    }

    /**
     * @see Assert#assertNotEquals(float, float, float, String)
     */
    public MultiAssertion assertNotEquals(float actual1, float actual2, float delta, String message) {
        assertions.add(() -> Assert.assertNotEquals(actual1, actual2, delta, message));
        return this;
    }

    /**
     * @see Assert#assertNotEquals(Object, Object, String)
     */
    public MultiAssertion assertNotEquals(Object actual1, Object actual2, String message) {
        assertions.add(() -> Assert.assertNotEquals(actual1, actual2, message));
        return this;
    }

    /**
     * @see Assert#assertTrue(boolean, String)
     */
    public MultiAssertion assertTrue(boolean condition, String message) {
        assertions.add(() -> Assert.assertTrue(condition, message));
        return this;
    }

    /**
     * @see Assert#assertFalse(boolean, String)
     */
    public MultiAssertion assertFalse(boolean condition, String message) {
        assertions.add(() -> Assert.assertFalse(condition, message));
        return this;
    }

    /**
     * @see Assert#assertSame(Object, Object, String)
     */
    public MultiAssertion assertSame(Object actual, Object expected, String message) {
        assertions.add(() -> Assert.assertSame(actual, expected, message));
        return this;
    }

    /**
     * @see Assert#assertNotSame(Object, Object, String)
     */
    public MultiAssertion assertNotSame(Object actual, Object expected, String message) {
        assertions.add(() -> Assert.assertNotSame(actual, expected, message));
        return this;
    }

    /**
     * @see Assert#assertNull(Object, String)
     */
    public MultiAssertion assertNull(Object object, String message) {
        assertions.add(() -> Assert.assertNull(object, message));
        return this;
    }

    /**
     * @see Assert#assertNotNull(Object, String)
     */
    public MultiAssertion assertNotNull(Object object, String message) {
        assertions.add(() -> Assert.assertNotNull(object, message));
        return this;
    }

    /**
     * @see Assert#assertThrows(Assert.ThrowingRunnable)
     */
    public MultiAssertion assertThrows(Assert.ThrowingRunnable runnable) {
        assertions.add(() -> Assert.assertThrows(runnable));
        return this;
    }

    /**
     * @see Assert#assertThrows(Class, Assert.ThrowingRunnable)
     */
    public MultiAssertion assertThrows(Class<? extends Throwable> throwableClass, Assert.ThrowingRunnable runnable) {
        assertions.add(() -> Assert.assertThrows(throwableClass, runnable));
        return this;
    }

    // custom assertions

    /**
     * Asserts that a given item is part of a collection
     *
     * @param collection a collection of items
     * @param item       a specific item which has to be in {@code collection}
     * @param message    the message to show when failing
     */
    public MultiAssertion assertContains(Collection<?> collection, Object item, String message) {
        assertions.add(() -> Assert.assertTrue(collection.contains(item), message));
        return this;
    }

    /**
     * Asserts that multiple items are part of a collection
     *
     * @param collection a collection of items
     * @param items      specific items which have to be in {@code collection}
     * @param message    the message to show when failing
     */
    public MultiAssertion assertContainsAll(Collection<?> collection, Collection<?> items, String message) {
        assertions.add(() -> Assert.assertTrue(collection.containsAll(items), message));
        return this;
    }

    /**
     * Asserts that at least one item is part of a collection
     *
     * @param collection a collection of items
     * @param items      specific items where at least one has to be in {@code collection}
     * @param message    the message to show when failing
     * @return
     */
    public MultiAssertion assertContainsAny(Collection<?> collection, Collection<?> items, String message) {
        assertions.add(() -> {
            for (Object item : items) {
                if (collection.contains(item)) {
                    return;
                }
                throw new AssertionError(message + "\n\nCollection doesn't contain any given item");
            }
        });
        return this;
    }

    public static class MultiAssertionError extends RuntimeException {
        private final List<Throwable> throwables;
        private final String message;

        public MultiAssertionError(List<Throwable> throwables) {
            if (throwables == null || throwables.isEmpty()) {
                throw new IllegalArgumentException("MultiAssertionError has to be constructed with at least one exception");
            }

            this.throwables = throwables;

            final StringJoiner joiner = new StringJoiner("\n\n");
            throwables.forEach(t -> joiner.add(t.getMessage()));
            message = joiner.toString();
        }

        @Override
        public String getMessage() {
            return message;
        }

        public List<Throwable> getThrowables() {
            return Collections.unmodifiableList(throwables);
        }
    }
}
