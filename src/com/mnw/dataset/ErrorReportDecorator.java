package com.mnw.dataset;

import java.util.List;

/** TODO description of this class is missing */
public interface ErrorReportDecorator {
    Throwable decorateTestCaseFailed(Throwable originalThrowable, int testCaseNo, String hint, Object[] testVector);

    DataSetTestRunSummary createTestHeader(int testVectorCount, List<OriginalExceptionWrapper> failedTestCases);
    Throwable createTestFooter(List<OriginalExceptionWrapper> failedTestCases);
}
