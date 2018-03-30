package com.anecon.taf.core;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A superclass for creating instances.
 * <p>
 * One responsibility of this class is to call {@link Activatable#activate()} when an instance
 * implementing this interface is retrieved.
 * <p>
 * An InstanceManager itself is {@link Cleanable} - it will look up all the created instances to call the
 * appropriate cleanUp methods of them.
 * <p>
 * Registration is simply done by calling {@code getInstance},
 * passing the class and an optional list of parameters to instantiate it.
 *
 * @author Markus Möslinger
 */
public abstract class InstanceManager implements Cleanable {
    private final Map<ConstructionInformation, Object> instanceMap = new HashMap<>();

    private static final Logger log = LoggerFactory.getLogger(InstanceManager.class);

    /**
     * Returns an instance of {@code clazz} - if an existing one, constructed with the same parameters, could be found,
     * this one will be returned. Otherwise, a new one will be created.
     * <p>
     * If the provider is also an instance of {@code Activatable}, {@code activate()} will be called before returning it
     * <p>
     * Be aware that {@code clazz} needs to have an accessible constructor matching the passed {@code parameters},
     * otherwise an {@link AutomationFrameworkException} will be thrown.
     *
     * @param clazz      The test provider to get
     * @param parameters Optional parameters needed to initialize a new instance
     * @param <T>        The type of the provider
     * @return An instance of the requested class - either an existing or a new one.
     * @throws AutomationFrameworkException when a new instance couldn't be created
     * @see Activatable
     * @see Cleanable
     */
    @SuppressWarnings("unchecked")
    protected <T> T getInstance(Class<T> clazz, Object... parameters) {
        final ConstructionInformation constructionInformation = new ConstructionInformation(clazz, parameters);
        T provider;

        if (instanceMap.containsKey(constructionInformation)) {
            // get an existing one...
            provider = (T) instanceMap.get(constructionInformation);
            log.trace("Returning already created instance for {}", constructionInformation);
        } else {
            // ...or try to create a new one
            try {
                provider = ConstructorUtils.invokeConstructor(clazz, parameters);
                instanceMap.put(constructionInformation, provider);
                log.trace("Created new instance for {}", constructionInformation);
            } catch (NoSuchMethodException | IllegalAccessException e) {
                throw new AutomationFrameworkException("Found no matching constructor for " + constructionInformation, e);
            } catch (InvocationTargetException | InstantiationException e) {
                throw new AutomationFrameworkException("Construction threw an exception for " + constructionInformation, e);
            }
        }

        // if we have a UiKeywordProvider, automatically call activate
        // this will focus the window, or other stuff
        if (provider instanceof Activatable) {
            ((Activatable) provider).activate();
        }

        return provider;
    }

    @Override
    public void cleanUp() {
        instanceMap.entrySet().stream().filter(entry -> entry.getValue() instanceof Cleanable).forEach(entry -> {
            log.debug("Cleaning test provider " + entry.getKey());
            ((Cleanable) entry.getValue()).cleanUp();
        });

        instanceMap.clear();
    }

    @Override
    public void emergencyCleanUp() {
        instanceMap.entrySet().stream().filter(entry -> entry.getValue() instanceof Cleanable).forEach(entry -> {
            try {
                log.debug("Ungracefully cleaning: " + entry.getKey().toString());
                ((Cleanable) entry.getValue()).emergencyCleanUp();
            } catch (Exception e) {
                log.warn("Ungraceful cleanup not successful for " + entry.getKey(), e);
            }
        });

        instanceMap.clear();
    }

    /**
     * This class is used to hold information used for constructing instances. By using this for the {@code instanceMap},
     * {@code InstanceManager} is able to differentiate between instances of the same class, but requested with different
     * parameters.
     */
    private class ConstructionInformation {
        private final Class<?> clazz;
        private final Object[] parameters;

        ConstructionInformation(Class<?> clazz, Object[] parameters) {
            this.clazz = clazz;
            this.parameters = parameters;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            ConstructionInformation that = (ConstructionInformation) o;

            return new EqualsBuilder()
                    .append(clazz, that.clazz)
                    .append(parameters, that.parameters)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(clazz)
                    .append(parameters)
                    .toHashCode();
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("class: " + clazz.getSimpleName());
            if (parameters.length > 0) {
                sb.append("; Parameters: ").append(Arrays.toString(parameters));
            }

            return sb.toString();
        }
    }
}
