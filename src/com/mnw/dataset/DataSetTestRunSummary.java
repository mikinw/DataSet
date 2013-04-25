package com.mnw.dataset;

/** TODO description of this class is missing */
public class DataSetTestRunSummary extends AssertionError {
    public DataSetTestRunSummary(String summary) {
        super(summary);
        setStackTrace(new StackTraceElement[0]);
    }
}
