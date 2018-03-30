package com.anecon.taf.core.reporting;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Advice for reporting every occurring TestNG Assert call
 */
@Aspect
public class AssertReporter {
    private static final Logger log = LoggerFactory.getLogger(AssertReporter.class);
    private static final Set<String> UNARY_ASSERTS = new HashSet<>();

    static {
        UNARY_ASSERTS.addAll(Arrays.asList(
                "assertFalse",
                "assertTrue",
                "assertNull",
                "assertNotNull"));
    }

    @Around("call(void org.testng.Assert.assert*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        final Method method = MethodSignature.class.cast(point.getSignature()).getMethod();
        final String message = getAssertionMessage(method, point.getArgs())
                .orElse("Performing assertion: " + method.getName());

        try {
            Object result = point.proceed();
            Report.pass(message, "Pass");
            if (ReportingConfig.get().screenshotOnAssertionPass()) {
                Report.takeScreenshot(message);
            }
            return result;
        } catch (Throwable t) {
            Report.error(message, "Fail");
            if (ReportingConfig.get().screenshotOnAssertionFail()) {
                Report.takeScreenshot(message);
            }
            throw t;
        }
    }

    private static Optional<String> getAssertionMessage(Method method, Object[] args) {
        final String methodName = method.getName();
        log.trace("Assert {} called with args {}", methodName, args);

        final int parameterCount = args.length;

        // message is either second or third parameter
        if (UNARY_ASSERTS.contains(methodName) && parameterCount == 2 || parameterCount == 3) {
            try {
                final String message = (String) args[args.length - 1];
                log.trace("Message is {}", message);

                return Optional.of(message);
            } catch (ClassCastException e) {
                // this shouldn't happen, as the last argument should be a String in our case, but anyway...
                log.warn("Couldn't cast last argument", e);

                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
