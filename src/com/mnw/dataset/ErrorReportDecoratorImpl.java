package com.mnw.dataset;

import java.util.Iterator;
import java.util.List;

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
    public DataSetTestRunSummary createTestHeader(int testVectorCount, List<OriginalExceptionWrapper> failedTestCases) {
        String summary = createHeaderSummary(testVectorCount, failedTestCases);
        return new DataSetTestRunSummary(summary);
    }

    private String createHeaderSummary(int testCasesCount, List<OriginalExceptionWrapper> failedTestCases) {
        StringBuilder ret = new StringBuilder(100);
        ret.append(failedTestCases.size());
        ret.append(" tests out of ");
        ret.append(testCasesCount);
        ret.append(" failed.");
        ret.append(System.getProperty("line.separator"));
        ret.append("Failed cases: [");
        Iterator<OriginalExceptionWrapper> iterator = failedTestCases.iterator();
        while(iterator.hasNext()) {
            ret.append(iterator.next().getTestCaseNo());
            if (iterator.hasNext()) {
                ret.append(", ");
            }
        }
        ret.append("]");
        return ret.toString();
    }

    @Override
    public Throwable createTestFooter(List<OriginalExceptionWrapper> failedTestCases) {
        for (OriginalExceptionWrapper failedTestCase : failedTestCases) {
            if (failedTestCase.isSerious()) {
                return new DataSetTestingEndedWithException();
            }
        }
        Throwable ret = new AssertionError("Only Assertion errors were during the testrun");
        ret.setStackTrace(new StackTraceElement[0]);
        return ret;
    }
}
