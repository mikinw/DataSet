package com.mnw.dataset;

import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("OverlyBroadCatchBlock")
abstract class DataSetStatement extends Statement {
    protected final OriginalExceptionWrapperFactory mOriginalExceptionWrapperFactory;
    private final FailureVerifier mFailureVerifier;
    protected Statement mOriginalStatement;
    protected ErrorReportDecorator mErrorReportDecorator;
    private TestCaseable testCases;
    int mTestCaseNo;

    /**
     * This array is instantiated during the test function evaluation. If a valid DataSet.testData
     * is present. The testData should <br>
     * <ul>
     * <li>- be a public static class
     * <li>- implement TestCaseable interface
     * <li>- the implemented interface should return a 2 dimensional array (this array will be
     * assigned to this member variable.
     * </ul>
     * These values can be accessed by the Rule.getParameter() call.
     * <p>If expectedExceptionFirst is set, than the evaluation expects a Class as the first
     * element of each row. This is handled by the evaluation and in this case parameters can
     * be requested from the second ([1] since it is zero based) element.
     */
    protected Object[] mTestVector;

    protected DataSetStatement(Statement statement,
                     TestCaseable dataSet,
                     ErrorReportDecorator errorReportDecorator,
                     OriginalExceptionWrapperFactory originalExceptionWrapperFactory,
                     FailureVerifier failureVerifier) {
        mOriginalStatement = statement;
        testCases = dataSet;
        mErrorReportDecorator = errorReportDecorator;
        mOriginalExceptionWrapperFactory = originalExceptionWrapperFactory;
        mFailureVerifier = failureVerifier;
    }

    protected abstract void evaluateTestCase() throws OriginalExceptionWrapper, InvalidDataSetException;
    abstract Object getParameter(int i) throws InvalidDataSetException;

    public int getTestCaseNumber() {
        return mTestCaseNo;
    }

    @Override
    public void evaluate() throws Throwable {
        List<OriginalExceptionWrapper> failedTestCases = evaluateAll();

        List<Throwable> outputThrowableList = analyseFailedTestVectors(failedTestCases);

        mFailureVerifier.assertEmpty(outputThrowableList);
    }

    private List<Throwable> analyseFailedTestVectors(List<OriginalExceptionWrapper> failedTestCases) {
        List<Throwable> outputThrowableList = new ArrayList<Throwable>();
        if (!(failedTestCases.isEmpty())) {
            outputThrowableList.add(mErrorReportDecorator.createTestHeader(testCases.getCount(), failedTestCases));
            for (OriginalExceptionWrapper failedTestCase : failedTestCases) {
                outputThrowableList.add(failedTestCase.decorateTestCaseFailed(mErrorReportDecorator));
            }
            outputThrowableList.add(mErrorReportDecorator.createTestFooter(failedTestCases));
        }
        return outputThrowableList;
    }

    // run evaluate with setting parameters one row by row
    private List<OriginalExceptionWrapper> evaluateAll() {
        mTestCaseNo = 0;
        List<OriginalExceptionWrapper> failedTestCases = new ArrayList<OriginalExceptionWrapper>();
        for (int to = testCases.getCount(); mTestCaseNo < to; mTestCaseNo++) {
            mTestVector = testCases.getTestCase(mTestCaseNo);
            try {
                evaluateTestCase();
            } catch (OriginalExceptionWrapper failedTestCase) {
                failedTestCases.add(mOriginalExceptionWrapperFactory.create(failedTestCase, mTestCaseNo, mTestVector));
            } catch (Throwable failedTestCase) {
                failedTestCases.add(mOriginalExceptionWrapperFactory.create(failedTestCase, mTestCaseNo, mTestVector));
            }
        }
        return failedTestCases;
    }


}

