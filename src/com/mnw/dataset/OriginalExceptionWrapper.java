package com.mnw.dataset;

/**
 * Stores additional information about the thrown Throwable, such as testVector, index of the testVector
 * and additional description (if any). This information will later be used to create formatted error
 * message.
 */

public class OriginalExceptionWrapper extends Throwable {
    public final static int TEST_CASE_NUMBER_NOT_SET = -1;
    private final int mTestCaseNo;
    private final String mHint;
    private final Throwable mOriginalThrowable;
    private final Object[] mTestVector;

    public OriginalExceptionWrapper(Throwable originalThrowable, int testCaseNo, String hint, Object[] testVector) {
        super();
        mTestCaseNo = testCaseNo;
        mHint = hint;
        mOriginalThrowable = originalThrowable;
        mTestVector = testVector;
    }

    public OriginalExceptionWrapper(Throwable originalThrowable, String hint) {
        this(originalThrowable, TEST_CASE_NUMBER_NOT_SET, hint, new Object[0]);
    }

    public OriginalExceptionWrapper(OriginalExceptionWrapper originalThrowable, int testCaseNo, String hint, Object[] testVector) {
        this(originalThrowable.mOriginalThrowable, testCaseNo, originalThrowable.mHint + hint, testVector);
    }

    public Throwable decorateTestCaseFailed(ErrorReportDecorator errorReportDecorator) {
        return errorReportDecorator.decorateTestCaseFailed(mOriginalThrowable, mTestCaseNo, mHint, mTestVector);
    }

    public boolean isSerious() {
        return !(mOriginalThrowable instanceof AssertionError) && !(mOriginalThrowable instanceof org.junit.internal.AssumptionViolatedException);
    }

    public boolean isSkipped() {
        return (mOriginalThrowable instanceof org.junit.internal.AssumptionViolatedException);
    }

    public boolean isAssertionError() {
        return (mOriginalThrowable instanceof AssertionError);
    }

    public int getTestCaseNo() {
        return mTestCaseNo;
    }

    public boolean hasInvalidDatasetWarning() {
        return mOriginalThrowable instanceof InvalidDataSetException;
    }

    public String getInvalidDataSetWarning() {
        return mOriginalThrowable instanceof InvalidDataSetException ? mOriginalThrowable.getLocalizedMessage() : "";
    }
}
