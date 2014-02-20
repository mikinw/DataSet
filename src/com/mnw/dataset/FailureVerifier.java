package com.mnw.dataset;

import java.util.List;

/**
 * Simple wrapper that wraps MultipleFailureException in order to be able to inject it.
 */
public class FailureVerifier {
    public void assertEmpty(List<Throwable> outputThrowableList) throws Throwable {
        MultipleFailureOrSkipException.assertOnlyFailureOrError(outputThrowableList);
    }
}
