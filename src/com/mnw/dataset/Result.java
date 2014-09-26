package com.mnw.dataset;

/**
 * Holds the result of one test run. It can be pass, assertError, exceptionError, assumptionError. It it
 * didn't pass, than it has the stack trace also.
 * isAssertionFailure() is true if an {@link java.lang.AssertionError} was thrown in the test run.
 * isSkipped() is true if an {@link org.junit.internal.AssumptionViolatedException} was thrown in the test run.
 * isSerious() is true if any {@link java.lang.Exception} was thrown in the test run.
 */
public interface Result {
    boolean isAssertionFailure();
    boolean isSkipped();
    boolean isSerious();
}
