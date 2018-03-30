package com.anecon.taf.core;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class InstanceManagerTest {
    private static final int NO_ARG_CONSTRUCTOR_VALUE = 0;
    private static final int ARG_CONSTRUCTOR_VALUE = 1;

    private InstanceManager instanceManager;

    @BeforeMethod
    public void setUp() {
        instanceManager = new InstanceManager() {};
    }

    @Test
     public void getInstanceReturnsAnInstance() {
        assertEquals(NO_ARG_CONSTRUCTOR_VALUE, instanceManager.getInstance(DefaultConstructorClass.class).getValue());
    }

    @Test
    public void callingGetInstanceTwiceReturnsTheIdenticalInstance() {
        DefaultConstructorClass i1 = instanceManager.getInstance(DefaultConstructorClass.class);
        DefaultConstructorClass i2 = instanceManager.getInstance(DefaultConstructorClass.class);

        assertTrue(System.identityHashCode(i1) == System.identityHashCode(i2));
    }

    @Test
    public void getInstanceChoosesConstructorWithoutParametersWhenThereAreNoParametersPassed() {
        MultipleConstructorClass i1 = instanceManager.getInstance(MultipleConstructorClass.class, NO_ARG_CONSTRUCTOR_VALUE);

        assertEquals(NO_ARG_CONSTRUCTOR_VALUE, i1.getValue());
    }

    @Test
    public void getInstanceChoosesConstructorWithCorrectParametersWhenThereAreParametersPassed() {
        MultipleConstructorClass i1 = instanceManager.getInstance(MultipleConstructorClass.class, ARG_CONSTRUCTOR_VALUE);

        assertEquals(ARG_CONSTRUCTOR_VALUE, i1.getValue());
    }

    @Test
    public void getInstanceConstructsNewInstanceWithDifferentParameters() {
        MultipleConstructorClass i1 = instanceManager.getInstance(MultipleConstructorClass.class, 1);
        MultipleConstructorClass i2 = instanceManager.getInstance(MultipleConstructorClass.class, 2);

        assertEquals(1, i1.getValue());
        assertEquals(2, i2.getValue());
    }

    @Test
    public void getInstanceReturnsSameInstanceWithIdenticalParameters() {
        MultipleConstructorClass i1 = instanceManager.getInstance(MultipleConstructorClass.class, 1);
        MultipleConstructorClass i2 = instanceManager.getInstance(MultipleConstructorClass.class, 1);

        assertTrue(System.identityHashCode(i1) == System.identityHashCode(i2));
    }

    public static class DefaultConstructorClass {
        private final int value = NO_ARG_CONSTRUCTOR_VALUE;

        public int getValue() {
            return value;
        }
    }

    public static class MultipleConstructorClass {
        private final int value;

        public MultipleConstructorClass() {
            this.value = NO_ARG_CONSTRUCTOR_VALUE;
        }

        public MultipleConstructorClass(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}