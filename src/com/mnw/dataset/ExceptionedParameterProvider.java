package com.mnw.dataset;

/**
 * TODO description of this class is missing
 */
public class ExceptionedParameterProvider implements ParameterProvider {
    private Object[] mTestVector;

    public ExceptionedParameterProvider(Object[] testVector) {
        mTestVector = testVector;
    }

    @Override
    public Object getParameter(int i) throws InvalidDataSetException {
        if (mTestVector == null) {
            throw new InvalidDataSetException("No DataSet defined, in spite of the fact that getParameter() has been called");
        }
        if (i == 0) {
            throw new InvalidDataSetException(
                    "0th parameter requested, while expectedExceptionFirst is set. First requestable parameter is getParameter(1)");
        }
        if (mTestVector.length <= i || i < 1) {
            throw new InvalidDataSetException("Requested parameter is out of the array");
        }
        return mTestVector[i];
    }
}
