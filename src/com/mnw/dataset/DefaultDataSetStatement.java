package com.mnw.dataset;

/**
 * Default {@link DataSetMultiStatement} implementation, which runs (evaluates) the test as is, wraps its
 * result and gives access to the files of the test vector.
 */
public class DefaultDataSetStatement /*extends DataSetStatement*/ {

/*    public DefaultDataSetStatement(Statement original, TestCaseable dataSet) {
        super(original,
             dataSet,
             new ErrorReportDecoratorImpl(),
             new OriginalExceptionWrapperFactory(),
             new FailureVerifier(),
             testCaseEvaluator, statementComponentFactoryXXX);
    }*/

/*    protected DefaultDataSetStatement(Statement statement,
                                      TestCaseable dataSet,
                                      ErrorReportDecorator errorReportDecorator,
                                      OriginalExceptionWrapperFactory originalExceptionWrapperFactory,
                                      FailureVerifier failureVerifier) {
        super(statement,
              dataSet,
              errorReportDecorator,
              originalExceptionWrapperFactory,
              failureVerifier);
    }*/

/*    @Override
    Object getParameter(int i) throws InvalidDataSetException {
        if (mTestVector == null) {
            throw new InvalidDataSetException("No DataSet defined, in spite of the fact that getParameter() has been called");
        }
        if (mTestVector.length < i || i < 0) {
            throw new InvalidDataSetException("Requested parameter is out of the array");
        }
        return mTestVector[i];
    }*/

/*    @Override
    protected void evaluateTestCase() throws OriginalExceptionWrapper, InvalidDataSetException {
        try {
            mOriginalStatement.evaluate();

        } catch (Throwable evaluateException) {
        // gather more information on the exception
        // if we didn't expect exception (no expectedExceptionFirst set) we add the row information and throw the exception further
            throw mOriginalExceptionWrapperFactory.create(evaluateException, "");
        }
    }*/
}
