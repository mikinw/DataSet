package com.mnw.dataset;

import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("OverlyBroadCatchBlock")
class DataSetStatement extends Statement {
    private final OriginalExceptionWrapperFactory mOriginalExceptionWrapperFactory;
    private final FailureVerifier mFailureVerifier;
    private final ErrorReportDecorator mErrorReportDecorator;
    private final TestCaseable mTestCases;
    private final TestCaseEvaluator mTestCaseEvaluator;
    private final StatementComponentFactory mStatementComponentFactory;
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
//    private Object[] mTestVector;
    private ParameterProvider mParameterProvider;

    protected DataSetStatement(TestCaseable dataSet,
                               ErrorReportDecorator errorReportDecorator,
                               OriginalExceptionWrapperFactory originalExceptionWrapperFactory,
                               FailureVerifier failureVerifier,
                               TestCaseEvaluator testCaseEvaluator,
                               StatementComponentFactory statementComponentFactory) {
        mTestCases = dataSet;
        mErrorReportDecorator = errorReportDecorator;
        mOriginalExceptionWrapperFactory = originalExceptionWrapperFactory;
        mFailureVerifier = failureVerifier;
        this.mTestCaseEvaluator = testCaseEvaluator;
        mStatementComponentFactory = statementComponentFactory;
    }

    public Object getParameter(int i) throws InvalidDataSetException {
        return mParameterProvider.getParameter(i);
    }

    public int getTestCaseNumber() {
        return mTestCaseNo;
    }

    @Override
    public void evaluate() throws Throwable {
        Results results = evaluateAll();

        List<Throwable> outputThrowableList = analyseFailedTestVectors(results);

        mFailureVerifier.assertEmpty(outputThrowableList);
    }

    private List<Throwable> analyseFailedTestVectors(Results testResults) {
        List<Throwable> outputThrowableList = new ArrayList<Throwable>();

        // add header if error, assert, assumption
        // footer is the most serious exception
        if (testResults.isMostSeriousAssumptionFailure()) {
            outputThrowableList.add(mErrorReportDecorator.createOnlyAssumptionTestFooter(testResults));
            return outputThrowableList;
        }

        if (testResults.hasFailure()) {
            outputThrowableList.add(mErrorReportDecorator.createTestHeader(mTestCases.getCount(), testResults));
            for (OriginalExceptionWrapper testResult : testResults) {
                if (!testResult.isPassedTest()) {
                    outputThrowableList.add(testResult.decorateTestCaseFailed(mErrorReportDecorator));
                }
            }
            outputThrowableList.add(mErrorReportDecorator.createTestFooter(testResults));
        }
        return outputThrowableList;
    }

    // run evaluate with setting parameters one row by row
    private Results evaluateAll() {
        mTestCaseNo = 0;
        Results results = new Results();
        for (int to = mTestCases.getCount(); mTestCaseNo < to; mTestCaseNo++) {

            final Object[] testVector = mTestCases.getTestCase(mTestCaseNo);
            mParameterProvider = mStatementComponentFactory.createParameterProvider(testVector);
            try {
                mTestCaseEvaluator.evaluateTestCase(testVector);
            } catch (OriginalExceptionWrapper failedTestCase) {
                results.add(mOriginalExceptionWrapperFactory.create(failedTestCase, mTestCaseNo, testVector));
            } catch (InvalidDataSetException invalidDataSetException) {
                results.add(mOriginalExceptionWrapperFactory.create(invalidDataSetException, mTestCaseNo, testVector));
            } catch (PassedTestCaseException passedTestCaseException) {
                results.add(mOriginalExceptionWrapperFactory.create(passedTestCaseException, mTestCaseNo, testVector));
            } catch (Throwable failedTestCase) {
                results.add(mOriginalExceptionWrapperFactory.create(failedTestCase, mTestCaseNo, testVector));
            }
        }
        return results;
    }


}

