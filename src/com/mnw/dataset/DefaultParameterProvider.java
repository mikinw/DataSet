package com.mnw.dataset;

/**
 * TODO description of this class is missing
 */
public class DefaultParameterProvider implements ParameterProvider {
    private final Object[] mTestVector;

    public DefaultParameterProvider(Object[] testVector) {

        mTestVector = testVector;
    }

    @Override
    public Object getParameter(int i) throws InvalidDataSetException {
        if (mTestVector == null) {
            throw new InvalidDataSetException("No DataSet defined, in spite of the fact that getParameter() has been called");
        }
        if (mTestVector.length < i || i < 0) {
            throw new InvalidDataSetException("Requested parameter is out of the array");
        }
        return mTestVector[i];
    }
}
