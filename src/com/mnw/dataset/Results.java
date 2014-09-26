package com.mnw.dataset;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Holds all the {@link Result}s of the all the test runs.
 *
 */
public class Results implements Iterable<Result> {
    private final List<Result> mTestCaseResults = new ArrayList<Result>();
    private int mAssertionFailureCount;
    private int mAssumptionFailureCount;
    private int mErrorCount;

    @Override
    public Iterator<Result> iterator() {
        return mTestCaseResults.iterator();
    }

    public void add(Result result) {
        mTestCaseResults.add(result);

        if (result.isSkipped()) {
            mAssumptionFailureCount++;
        }
        if (result.isAssertionFailure()) {
            mAssertionFailureCount++;
        }
        if (result.isSerious()) {
            mErrorCount++;
        }
    }

    public boolean hasSkipped() {
        return mAssumptionFailureCount > 0;
    }

    public boolean hasAssertionFailure() {
        return mAssertionFailureCount > 0;
    }

    public boolean hasSerious() {
        return mErrorCount > 0;
    }

    public int getTotalCount() {
        return mTestCaseResults.size();
    }

    public int getTotalFailureCount() {
        return mAssertionFailureCount + mAssumptionFailureCount + mErrorCount;
    }
}
