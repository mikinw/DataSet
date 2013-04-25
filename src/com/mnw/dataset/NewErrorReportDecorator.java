package com.mnw.dataset;

/** TODO description of this class is missing */
public class NewErrorReportDecorator implements ErrorReportDecorator {

    private Throwable mOriginalThrowable;
    private int mTestCaseNo;
    private String mHint;

    @Override
    public Throwable decorate(Throwable originalThrowable, int testCaseNo, String hint) {

        /* add Custom lines to the StackTrace
        final StackTraceElement[] orig = originalThrowable.getStackTrace();
        final StackTraceElement[] stackTrace = new StackTraceElement[orig.length + 1];
        for (int i = 1; i < orig.length+1; i++) {
            stackTrace[i] = orig[i-1];

        }
        stackTrace[0] = new StackTraceElement(originalThrowable.getClass().toString(), "dsf", "87adta0", 20);
        */

//                    thr.setStackTrace(stackTrace);

//                    ComparisonFailure

//                    mErrorCollector.addError(new InvalidDataSetException("khgf", thr));
//                    mErrorCollector.addError(thr);
        final String originalExceptionName = originalThrowable.getClass().toString();

        if (originalThrowable instanceof InvalidDataSetException) {
            return originalThrowable;
        }
        final TestCaseFailed decoratedError
                = new TestCaseFailed("number " + testCaseNo + ". " + hint + "\nFailed with " + originalExceptionName, originalThrowable);

        return decoratedError;
    }

    /*

            StringBuilder detail = new StringBuilder(100);
        detail.append("number ")
                .append(testCaseNo)
                .append(". ")
                .append(hint)
                .append(System.getProperty("line.separator"))
                .append("Failed with ")
                .append(originalExceptionName)
                .append(System.getProperty("line.separator"))
                .append("For the following test vector: < ");
        for(int i = 0; i < testVector.length; i++) {
            Object o = testVector[i];
            detail.append(o != null ? o.toString() : "null");
            if (i < testVector.length - 1) {
                detail.append(", ");
            }
        }
        detail.append(" >");
     */

    @Override
    public void wrap(Throwable originalThrowable, int testCaseNo, String hint) {
        mOriginalThrowable = originalThrowable;
        mTestCaseNo = testCaseNo;
        mHint = hint;
    }

    @Override
    public Throwable decorate() {
        final String originalExceptionName = mOriginalThrowable.getClass().toString();
        return new TestCaseFailed("number " + mTestCaseNo + ". " + mHint + "\nFailed with " + originalExceptionName, mOriginalThrowable);
    }
}
