package com.mnw.dataset;

import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("OverlyBroadCatchBlock")
class DataSetMultiStatement extends Statement {
    private final OriginalExceptionWrapperFactory mOriginalExceptionWrapperFactory;
    private final FailureVerifier mFailureVerifier;
    private final ErrorReportDecorator mErrorReportDecorator;
    private final TestCaseEvaluator mTestCaseEvaluator;
    private final StatementComponentFactory mStatementComponentFactory;
    private final ArrayList<DataSetStatement> mDataSetMultiStatements;

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
    private DataSetStatement mCurrentStatement;
    private Results mResults;

    protected DataSetMultiStatement(final ArrayList<DataSetStatement> dataSetMultiStatements,
                                    final TestCaseEvaluator testCaseEvaluator,
                                    final StatementComponentFactory statementComponentFactory,
                                    final OriginalExceptionWrapperFactory originalExceptionWrapperFactory,
                                    final ErrorReportDecorator errorReportDecorator,
                                    final FailureVerifier failureVerifier) {
        mDataSetMultiStatements = dataSetMultiStatements;
        mOriginalExceptionWrapperFactory = originalExceptionWrapperFactory;
        mErrorReportDecorator = errorReportDecorator;
        mFailureVerifier = failureVerifier;
        this.mTestCaseEvaluator = testCaseEvaluator;
        mStatementComponentFactory = statementComponentFactory;
    }

    public Object getParameter(int i) throws InvalidDataSetException {
        return mParameterProvider.getParameter(i);
    }

    public int getTestCaseNumber() {
        return mCurrentStatement.mOrderNumber;
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
            outputThrowableList.add(mErrorReportDecorator.createTestHeader(mDataSetMultiStatements.size(), testResults));
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
        mResults = new Results();

        for (DataSetStatement dataSetStatement : mDataSetMultiStatements) {
            mCurrentStatement = dataSetStatement;
            evaluateSingle(mCurrentStatement, mResults);
        }
        return mResults;
    }

    private void evaluateSingle(DataSetStatement currentStatement, Results results) {
        final Object[] testVector = currentStatement.mTestVector;
        final int testCaseNo = currentStatement.mOrderNumber;
        mParameterProvider = mStatementComponentFactory.createParameterProvider(testVector);

        try {
            mTestCaseEvaluator.evaluateTestCase(testVector);
        } catch (OriginalExceptionWrapper failedTestCase) {
            results.add(mOriginalExceptionWrapperFactory.create(failedTestCase, testCaseNo, testVector));
        } catch (InvalidDataSetException invalidDataSetException) {
            results.add(mOriginalExceptionWrapperFactory.create(invalidDataSetException, testCaseNo, testVector));
        } catch (PassedTestCaseException passedTestCaseException) {
            results.add(mOriginalExceptionWrapperFactory.create(passedTestCaseException, testCaseNo, testVector));
        } catch (Throwable failedTestCase) {
            results.add(mOriginalExceptionWrapperFactory.create(failedTestCase, testCaseNo, testVector));
        }
    }

}

