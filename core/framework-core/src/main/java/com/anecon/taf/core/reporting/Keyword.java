package com.anecon.taf.core.reporting;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to indicate that a method is a keyword and should be reported in the HTML report.
 * It's optional value can be used as a description for the test step - otherwise, the method name will be reported.
 *
 * The annotation can also be used on class level, marking all public methods as keywords; in this case,
 * it's value will be ignored for more detailed descriptions.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Keyword {
    String value() default "";

    Mode screenshotOnPass() default Mode.DEFAULT;
    Mode screenshotOnFail() default Mode.DEFAULT;

    /**
     * When {@code} hideArguments is set to true, this Keyword's parameter list won't be reported, ie. when passing
     * passwords or other sensitive data
     * @return if method arguments should be hidden
     */
    boolean hideArguments() default false;
}
