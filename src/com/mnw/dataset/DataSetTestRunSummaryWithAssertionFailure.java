package com.mnw.dataset;

/** TODO description of this class is missing */
public class DataSetTestRunSummaryWithAssertionFailure extends AssertionError {
    public DataSetTestRunSummaryWithAssertionFailure(String summary) {
        super(summary);
        setStackTrace(new StackTraceElement[0]);
    }
}
