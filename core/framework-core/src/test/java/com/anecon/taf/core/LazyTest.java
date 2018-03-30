package com.anecon.taf.core;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;


public class LazyTest {
    private static final String SOME_STRING = "";
    private boolean created;

    @BeforeMethod
    public void setUp() {
        created = false;
    }

    @Test
    public void lazyCanReturnAnInstance() {
        Lazy<String> s = Lazy.initializingWith(() -> SOME_STRING);

        assertEquals(s.get(), SOME_STRING);
    }

    @Test
    public void instanceIsOnlyCreatedWhenCallingGet() {
        Lazy<String> s = Lazy.initializingWith(() -> {markCreated(); return SOME_STRING;});

        assertFalse(created);
        assertEquals(s.get(), SOME_STRING);
        assertTrue(created);
    }

    private void markCreated() {
        created = true;
    }
}
