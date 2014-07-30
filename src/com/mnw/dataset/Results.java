package com.mnw.dataset;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * TODO description of this class is missing
 */
public class Results implements Iterable<OriginalExceptionWrapper> {
    private final List<OriginalExceptionWrapper> mTestCaseResults = new ArrayList<OriginalExceptionWrapper>();
    private int mAssertionFailureCount;
    private int mAssumptionFailureCount;
    private int mErrorCount;

    @Override
    public Iterator<OriginalExceptionWrapper> iterator() {
        return mTestCaseResults.iterator();
    }

    public void add(OriginalExceptionWrapper originalExceptionWrapper) {
        mTestCaseResults.add(originalExceptionWrapper);

        if (originalExceptionWrapper.isAssertionFailure()) {
            mAssertionFailureCount++;
        }
        if (originalExceptionWrapper.isSkipped()) {
            mAssumptionFailureCount++;
        }
        if (originalExceptionWrapper.isSerious()) {
            mErrorCount++;
        }
    }

    public boolean hasFailure() {
        return getTotalFailureCount() > 0;
    }

    public boolean hasErrorFailure() {
        return mErrorCount > 0;
    }

    public boolean isMostSeriousAssumptionFailure() {
        return mAssertionFailureCount + mErrorCount == 0 && mAssumptionFailureCount > 0;
    }

    public int getTotalCount() {
        return mTestCaseResults.size();
    }

    public int getTotalFailureCount() {
        return mAssertionFailureCount + mAssumptionFailureCount + mErrorCount;
    }
}
