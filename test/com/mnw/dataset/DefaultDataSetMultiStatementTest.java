package com.mnw.dataset;

import org.junit.Test;
import org.junit.runners.model.Statement;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class DefaultDataSetMultiStatementTest {

    private Statement stubStatement;
    private TestCaseable stubDataSet;
    private ErrorReportDecorator mockErrorReportDecorator;
    private OriginalExceptionWrapperFactory mockOriginalExceptionWrapperFactory;
    private FailureVerifier spyFailureVerifier;

    class MyStatement extends Statement {
        @Override
        public void evaluate() throws Throwable {
            throw new ClassCastException("laksjdf");
        }
    }

    class MyDataSet implements TestCaseable {

        @Override
        public Object[] getTestCase(int i) {
            return null;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }

    @Test
    public void asdf() throws Throwable {
        // init
        stubStatement = new MyStatement();
        stubDataSet = mock(TestCaseable.class); //new MyDataSet();
        ErrorReportDecorator mockErrorReportDecorator = mock(ErrorReportDecorator.class);
        mockOriginalExceptionWrapperFactory = new OriginalExceptionWrapperFactory(); //Mockito.mock(OriginalExceptionWrapperFactory.class);
        spyFailureVerifier = mock(FailureVerifier.class);
/*
        DefaultDataSetStatement sut = new DataSetStatement(stubStatement,
                                                                  stubDataSet,
                                                                  mockErrorReportDecorator,
                                                                  mockOriginalExceptionWrapperFactory,
                                                                  spyFailureVerifier);

        // run
        sut.evaluate();
*/

        // verify
    }

    // TODO:
    // if getTestCase returns null, -> InvalidDataSetException
    // if getTestCase array is smaller than getCount -> InvalidDataSE
    // if getTestCase array element is null -> getParam could throw Exception
    // if there was Exception (not AssertError) during evaluate, last element of errorReport should be DataSetTestingEndedWithException
    // if there are only AssertErrors, last element of ErrorReport is not DataSetTestingEndedWithException
    // if expectedExceptionFirst is set, but the first element of testVector is not an exception class -> IDSE
    // if there were X errors, ErrorReportDecorator.createTestHeader() should receive a list of size X
    // if there were X Exceptions and no AssertErrors, FailureVerifier.assertEmpty() should receive a list of size X+2, the first element should be DataSetTestRunSummary

    // getParam tests

}
