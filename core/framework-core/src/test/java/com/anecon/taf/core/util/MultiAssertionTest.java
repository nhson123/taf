package com.anecon.taf.core.util;

import com.anecon.taf.core.util.MultiAssertion.MultiAssertionError;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MultiAssertionTest {
    private MultiAssertion multi;

    @BeforeMethod(alwaysRun = true)
    public void createMultiAssertion() {
        multi = new MultiAssertion();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void noAssertionIllegalState() {
        multi.performAssertions();
    }

    @Test
    public void onePositiveAssertion() {
        multi.assertTrue(true, "assertTrue").performAssertions();
    }

    @Test
    public void multiplePositiveAssertions() {
        multi.assertTrue(true, "assertTrue").assertFalse(false, "assertFalse").performAssertions();
    }

    @Test(expectedExceptions = MultiAssertionError.class)
    public void oneNegativeAssertion() {
        try {
            multi.assertTrue(false, "assertFalse").performAssertions();
        } catch (MultiAssertionError e) {
            Assert.assertEquals(e.getThrowables().size(), 1, "The assertion should fail");
            throw e;
        }
    }

    @Test(expectedExceptions = MultiAssertionError.class)
    public void multipleNegativeAssertions() {
        try {
            multi.assertTrue(false, "assertTrue").assertFalse(true, "assertFalse").performAssertions();
        } catch (MultiAssertionError e) {
            Assert.assertEquals(e.getThrowables().size(), 2, "Both assertions assertions should fail");
            throw e;
        }
    }

    @Test(expectedExceptions = MultiAssertionError.class)
    public void positiveAndNegativeAssertionsMixed() {
        try {
            multi.assertTrue(false, "assertTrue").assertFalse(true, "assertFalse").assertEquals(0, 0, "assertEquals").performAssertions();
        } catch (MultiAssertionError e) {
            Assert.assertEquals(e.getThrowables().size(), 2, "2 of 3 assertions should fail");
            throw e;
        }
    }
}
