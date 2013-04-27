package com.mnw.dataset;

/**
 * instantiates OriginalExceptionWrapper
 */
public class OriginalExceptionWrapperFactory {
    public OriginalExceptionWrapper create(OriginalExceptionWrapper failedTestCase, int testCaseNo,
                                           Object[] testVector) {
        return new OriginalExceptionWrapper(failedTestCase, testCaseNo, "", testVector);
    }

    public OriginalExceptionWrapper create(Throwable failedTestCase, int testCaseNo, Object[] testVector) {
        return new OriginalExceptionWrapper(failedTestCase, testCaseNo, "", testVector);
    }

    public OriginalExceptionWrapper create(Throwable originalThrowable, String hint) {
        return new OriginalExceptionWrapper(originalThrowable, hint);
    }
}
