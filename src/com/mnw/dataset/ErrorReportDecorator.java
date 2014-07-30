package com.mnw.dataset;

/** TODO description of this class is missing */
public interface ErrorReportDecorator {
    Throwable decorateTestCaseFailed(Throwable originalThrowable, int testCaseNo, String hint, Object[] testVector);

    DataSetTestRunSummaryWithAssertionFailure createTestHeader(int testVectorCount, Results failedTestCases);
    Throwable createTestFooter(Results testResults);
    Throwable createOnlyAssumptionTestFooter(Results testResults);
}
