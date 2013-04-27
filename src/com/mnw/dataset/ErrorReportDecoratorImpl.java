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

        Iterator<OriginalExceptionWrapper> iterator = failedTestCases.iterator();
        StringBuilder failedTestList = new StringBuilder(10);
        String invalidDataSetWarning = "";
        while(iterator.hasNext()) {
            final OriginalExceptionWrapper next = iterator.next();
            failedTestList.append(next.getTestCaseNo());
            if (iterator.hasNext()) {
                failedTestList.append(", ");
            }
            if (invalidDataSetWarning.isEmpty()) {
                invalidDataSetWarning = " Invalid dataset. " + next.getInvalidDataSetWarning();
            }
        }


        ret.append(failedTestCases.size())
            .append(" tests out of ")
            .append(testCasesCount)
            .append(" failed.")
            .append(invalidDataSetWarning)
            .append(System.getProperty("line.separator"))
            .append("Failed cases (zero based): [")
            .append(failedTestList)
            .append("]");
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
