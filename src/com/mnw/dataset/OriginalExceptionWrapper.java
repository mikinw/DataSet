package com.mnw.dataset;

/**
 * TODO description of this class is missing
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

    public Throwable createTestCaseFailed() {
        return (new NewErrorReportDecorator()).decorate(mOriginalThrowable, mTestCaseNo, mHint);
    }

    public boolean isSerious() {
        return !(mOriginalThrowable instanceof AssertionError);
    }

    public int getTestCaseNo() {
        return mTestCaseNo;
    }
}
