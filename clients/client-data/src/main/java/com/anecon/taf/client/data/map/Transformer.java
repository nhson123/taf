package com.anecon.taf.client.data.map;

/**
 * Transforms a {@link String} value to a target type
 *
 * @param <T> the target type to transform the {@link String} to
 */
public interface Transformer<T> {
    /**
     * Transforms a {@link String} value to a target type
     *
     * @param source the {@link String} to transform
     * @return if {@code source} is null or empty: {@code null}<br/>
     * otherwise: a new instance of {@code T}
     */
    T transform(String source);
}
