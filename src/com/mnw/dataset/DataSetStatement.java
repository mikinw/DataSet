package com.mnw.dataset;

import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

abstract class DataSetStatement extends Statement {
    protected Statement mOriginalStatement;
    protected ErrorReportDecorator mErrorReportDecorator;
    private DataSet mDataSet;
    int mTestCaseNo;

    private List<Throwable> mThrowableList = new ArrayList<Throwable>();
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
    protected Object[] mTestCase;

    protected DataSetStatement(Statement original, DataSet dataSet) {
        mOriginalStatement = original;
        mDataSet = dataSet;
        mErrorReportDecorator = new ErrorReportDecoratorImpl();
    }

    protected abstract void evaluateTestCase() throws OriginalExceptionWrapper, InvalidDataSetException;
    abstract Object getParameter(int i) throws InvalidDataSetException;

    public int getTestCaseNumber() {
        return mTestCaseNo;
    }

    @Override
    public void evaluate() throws Throwable {
        mTestCase = null;
        mTestCaseNo = 0;

        TestCaseable testCases = createTestData();

        // run evaluate with setting parameters one row by row
        mTestCaseNo = 0;
        List<OriginalExceptionWrapper> failedTestCases = new ArrayList<OriginalExceptionWrapper>();
        for (int to = testCases.getCount(); mTestCaseNo < to; mTestCaseNo++) {
            mTestCase = testCases.getTestCase(mTestCaseNo);
            try {
                evaluateTestCase();
            } catch (OriginalExceptionWrapper failedTestCase) {
                failedTestCases.add(new OriginalExceptionWrapper(failedTestCase, mTestCaseNo, "", mTestCase));
            } catch (Throwable failedTestCase) {
                failedTestCases.add(new OriginalExceptionWrapper(failedTestCase, mTestCaseNo, "", mTestCase));
            }
        }

        if (!(failedTestCases.isEmpty())) {
            String summary = createSummary(testCases.getCount(), failedTestCases);
            mThrowableList.add(new DataSetTestRunSummary(summary));

            for (OriginalExceptionWrapper failedTestCase : failedTestCases) {
                mThrowableList.add(failedTestCase.createTestCaseFailed());
            }

            for (OriginalExceptionWrapper failedTestCase : failedTestCases) {
                if (failedTestCase.isSerious()) {
                    mThrowableList.add(new DataSetTestingEndedWithException());
                }
            }
        }

        MultipleFailureException.assertEmpty(mThrowableList);
    }

    private String createSummary(int testCasesCount, List<OriginalExceptionWrapper> failedTestCases) {
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

    // try to instantiate and set the test data
    private TestCaseable createTestData() throws IllegalAccessException, InvalidDataSetException {
        TestCaseable testCases;
        try {
            Class<?> testDataClass = mDataSet.testData();
            testCases = (TestCaseable) testDataClass.newInstance();

        } catch (InstantiationException ie) {
            throw new InvalidDataSetException(
                    "Can't instantiate given DataSet. It may reference some abstract method or it isn't static class",
                    ie);
        }
        return testCases;
    }


}

