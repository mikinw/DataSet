package com.mnw.dataset;

import org.junit.internal.AssumptionViolatedException;

/**
 * Creates a {@link com.mnw.dataset.Result} for a test run.
 */
public class ResultFactory {

    public PassResult createPassResult() {
        return new PassResult();
    }

    public Result createSkippedResult(AssumptionViolatedException exception) {
        // TODO [mnw] implement method ResultFactory:createSkippedResult
        return null;
    }

    public Result createAssertionFailureResult(AssertionError exception) {
        // TODO [mnw] implement method ResultFactory:createAssertionFailureResult
        return null;

    }

    public Result createSeriousResult(Throwable throwable) {
        // TODO [mnw] implement method ResultFactory:createSeriousResult
        return null;
    }
}
