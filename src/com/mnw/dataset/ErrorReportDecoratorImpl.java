package com.mnw.dataset;

import org.junit.internal.AssumptionViolatedException;

import java.util.Iterator;

/**
 * Contains methods to create and format test run header, footer and Throwables that were thrown during test run.
 */
public class ErrorReportDecoratorImpl implements ErrorReportDecorator {

    @Override
    public Throwable decorateTestCaseFailed(Throwable originalThrowable, int testCaseNo, String hint, Object[] testVector) {
        if (originalThrowable instanceof InvalidDataSetException) {
            return originalThrowable;
        }

        final String originalExceptionName = originalThrowable.getClass().toString();
        StringBuilder detail = new StringBuilder(100);
        detail.append("number ")
                .append(testCaseNo)
                .append(". ")
                .append(hint)
                .append(System.getProperty("line.separator"))
                .append("Failed with ")
                .append(originalExceptionName)
                .append(System.getProperty("line.separator"))
                .append("For the following test vector: < ")
                .append(decorateTestVector(testVector))
                .append(" >");

        final TestCaseFailed decoratedError = new TestCaseFailed(detail.toString(), originalThrowable);
        return decoratedError;
    }

    public String decorateTestVector(Object[] testVector) {
        if (testVector == null) {
            return "the test vector was null";
        }

        StringBuilder ret = new StringBuilder(100);
        for(int i = 0; i < testVector.length; i++) {
            Object o = testVector[i];
            ret.append(o != null ? o.toString() : "null");
            if (i < testVector.length - 1) {
                ret.append(", ");
            }
        }

        return ret.toString();
    }

    @Override
    public DataSetTestRunSummaryWithAssertionFailure createTestHeader(int testVectorCount, Results failedTestCases) {
        String summary = createHeaderSummary(testVectorCount, failedTestCases);
        return new DataSetTestRunSummaryWithAssertionFailure(summary);
    }

    private String createHeaderSummary(int testCasesCount, Results failedTestCases) {
        StringBuilder ret = new StringBuilder(100);

        int failedTestCount = 0;
        int skippedTestCount = 0;
        Iterator<OriginalExceptionWrapper> iterator = failedTestCases.iterator();
        StringBuilder failedTestList = new StringBuilder(10);
        StringBuilder skippedTestList = new StringBuilder(10);
        String invalidDataSetWarning = "";
        while(iterator.hasNext()) {
            final OriginalExceptionWrapper next = iterator.next();
            if (next.isSkipped()) {
                if (skippedTestCount > 0) skippedTestList.append(", ");
                skippedTestList.append(next.getTestCaseNo());
                skippedTestCount++;
            }

            if (next.isSerious() || next.isAssertionFailure()) {
                if (failedTestCount > 0) failedTestList.append(", ");
                failedTestList.append(next.getTestCaseNo());
                if (invalidDataSetWarning.isEmpty() && next.hasInvalidDatasetWarning()) {
                    invalidDataSetWarning = " Invalid dataset. " + next.getInvalidDataSetWarning();
                }
                failedTestCount++;
            }
        }


        if (!(invalidDataSetWarning.isEmpty())) {
            ret.append(invalidDataSetWarning);
            return ret.toString();
        }

        ret.append("Out of ")
            .append(testCasesCount)
            .append(" total tests ");
        if (failedTestCount > 0) {
            ret.append(failedTestCount)
                .append(" failed ");
        }
        if (skippedTestCount > 0) {
            ret.append(skippedTestCount)
                .append(skippedTestCount > 1 ? " were " : " was ")
                .append("skipped");
        }

        if (failedTestCount > 0) {
            ret
                .append(System.getProperty("line.separator"))
                .append("Failed cases (zero based): [")
                .append(failedTestList)
                .append("]");
        }
        if (skippedTestCount > 0) {
            ret
                .append(System.getProperty("line.separator"))
                .append("Skipped cases (zero based): [")
                .append(skippedTestList)
                .append("]");
        }
        return ret.toString();
    }

    @Override
    public Throwable createTestFooter(final Results testResults) {
//        Throwable mostSeriousError = null;
        if (testResults.hasErrorFailure()) {
            return new DataSetTestingEndedWithException();
        }

//        for (OriginalExceptionWrapper failedTestCase : testResults) {
//            if (failedTestCase.isSerious()) {
//            }

//            if (failedTestCase.isAssertionFailure()) {
//                mostSeriousError = new AssertionError("Assertion error(s) found.");
//                mostSeriousError.setStackTrace(new StackTraceElement[0]);
//            } else if (failedTestCase.isSkipped() && !(mostSeriousError instanceof AssertionError)) {
//                mostSeriousError = new AssumptionViolatedException("All tests cases skipped.");
//                mostSeriousError.setStackTrace(new StackTraceElement[0]);
//            }
//        }
        return new DataSetTestingEndedWithAssertionError();
//        return mostSeriousError;
    }

    @Override
    public Throwable createOnlyAssumptionTestFooter(Results testResults) {
        String summary = createHeaderSummary(testResults.getTotalCount(), testResults);

        final AssumptionViolatedException assumptionViolatedException = new AssumptionViolatedException(summary);
        assumptionViolatedException.setStackTrace(new StackTraceElement[0]);
        return assumptionViolatedException;
    }
}
