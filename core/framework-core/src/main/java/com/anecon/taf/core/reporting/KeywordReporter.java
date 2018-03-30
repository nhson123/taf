package com.anecon.taf.core.reporting;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.StringJoiner;

@Aspect
public class KeywordReporter {
    private static final Logger log = LoggerFactory.getLogger(KeywordReporter.class);
    private static final ReportingConfig.ReportingConfigSpec config = ReportingConfig.get();

    @Around("execution(* *(..)) && @annotation(com.anecon.taf.core.reporting.Keyword)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        final Method method = MethodSignature.class.cast(point.getSignature()).getMethod();
        final String methodName = method.getName();
        final Parameter[] params = method.getParameters();
        final Object[] args = point.getArgs();
        final Keyword annotation = method.getAnnotation(Keyword.class);
        final String annotatedDescription = annotation.value();

        final String description = annotatedDescription.isEmpty() ? "Keyword " + methodName : annotatedDescription;


        final String details;
        if (!annotation.hideArguments()) {
            final StringJoiner htmlLineJoiner = new StringJoiner("<br>");
            htmlLineJoiner.add(methodName);

            for (int i = 0; i < args.length; i++) {
                final Parameter param = params[i];
                htmlLineJoiner.add("  " + param.getType().getSimpleName()
                    + " " + param.getName() + " = " + args[i]);
            }

            details = htmlLineJoiner.toString();
        } else {
            details = "";
        }

        final boolean screenshotOnPass = annotation.screenshotOnPass() != Mode.DEFAULT ? annotation.screenshotOnPass().getValue() : config.screenshotOnKeywordPass();
        final boolean screenshotOnFail = annotation.screenshotOnFail() != Mode.DEFAULT ? annotation.screenshotOnFail().getValue() : config.screenshotOnKeywordFail();
        try {
            final Object result = point.proceed();
            Report.info(description, details);
            if (screenshotOnPass) {
                Report.takeScreenshot(description);
            }
            log.debug("Passed: {}", description);

            return result;
        } catch (Throwable ex) {
            log.info("Failed: {}", description, ex);
            Report.error(description, details);
            if (screenshotOnFail) {
                Report.takeScreenshot(description);
            }
            throw ex;
        }
    }
}
