package com.mnw.dataset;

import com.google.common.base.Preconditions;

/**
 * Parameter provider that takes the testVector as if there were only test parameters in it (no expected exception, no description).
 * It checks boundaries.
 */
public class DefaultParameterProvider implements ParameterProvider {
    private final Object[] mTestVector;

    public DefaultParameterProvider(Object[] testVector) {
        Preconditions.checkNotNull("ParameterPotImpl is not properly initialised (TestVector seems to be null)." +
                                           " DataSetRule should be initialised directly in the test class (not in the setUp()/@Before method).");
        mTestVector = testVector;
    }

    @Override
    public Object getParameter(int i) throws InvalidDataSetException {
        if (mTestVector == null) {
            throw new InvalidDataSetException("No DataSet defined, in spite of the fact that getParameter() has been called");
        }
        if (mTestVector.length < i || i < 0) {
            throw new InvalidDataSetException("Requested parameter is out of the array. Size is: " + mTestVector.length);
        }
        return mTestVector[i];
    }
}
