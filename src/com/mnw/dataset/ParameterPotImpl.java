package com.mnw.dataset;

import com.google.common.base.Preconditions;

/**
 * returns an element from the mTestCases 2 dimensional array. The first dimension is set by a
 * loop that calls evaluate as many times as the length of the array (first dimension). The
 * second dimension is provided by the parameter. The function checks if the requested Object is
 * out of boundaries or not. It is the callers responsibility to cast the returned Object to the
 * desired class if its not a Java primitive.
 * param parameterOrder represents the i.th element in the defined dataset row. This value should be returned.
 * return The i.th element of the dataset row of the current test iteration.
 */
public class ParameterPotImpl implements ParameterPot, ParameterCan {
    private ParameterProvider mParameterProvider;

    @Override
    public Object getParameter(int parameterOrder) throws InvalidDataSetException {
        Preconditions.checkNotNull(mParameterProvider, "Programmer Error, pls replace programmer: ParameterPotImpl is not properly initialised");
        return mParameterProvider.getParameter(parameterOrder);
    }

    @Override
    public String getString(int parameterOrder) throws InvalidDataSetException {
        final String parameter;
        try {
            parameter = (String)mParameterProvider.getParameter(parameterOrder);
        } catch (ClassCastException e) {
            throw new InvalidDataSetException("Parameter " + parameterOrder + " can't be casted to String.", e);
        }
        return parameter;
    }

    @Override
    public boolean getBoolean(int parameterOrder) throws InvalidDataSetException {
        try {
            return (Boolean)mParameterProvider.getParameter(parameterOrder);
        } catch (ClassCastException e) {
            throw new InvalidDataSetException("Parameter " + parameterOrder + " can't be casted to Integer.", e);
        }
    }

    @Override
    public int getInt(int parameterOrder) throws InvalidDataSetException {
        try {
            return (Integer)mParameterProvider.getParameter(parameterOrder);
        } catch (ClassCastException e) {
            throw new InvalidDataSetException("Parameter " + parameterOrder + " can't be casted to Integer.", e);
        }
    }

    @Override
    public long getLong(int parameterOrder) throws InvalidDataSetException {
        try {
            return (Long)mParameterProvider.getParameter(parameterOrder);
        } catch (ClassCastException e) {
            throw new InvalidDataSetException("Parameter " + parameterOrder + " can't be casted to Integer.", e);
        }
    }

    @Override
    public double getDouble(int parameterOrder) throws InvalidDataSetException {
        try {
            return (Double)mParameterProvider.getParameter(parameterOrder);
        } catch (ClassCastException e) {
            throw new InvalidDataSetException("Parameter " + parameterOrder + " can't be casted to Integer.", e);
        }
    }

    @Override
    public float getFloat(int parameterOrder) throws InvalidDataSetException {
        try {
            return (Float)mParameterProvider.getParameter(parameterOrder);
        } catch (ClassCastException e) {
            throw new InvalidDataSetException("Parameter " + parameterOrder + " can't be casted to Integer.", e);
        }
    }

    @Override
    public byte getByte(int parameterOrder) throws InvalidDataSetException {
        try {
            return (Byte)mParameterProvider.getParameter(parameterOrder);
        } catch (ClassCastException e) {
            throw new InvalidDataSetException("Parameter " + parameterOrder + " can't be casted to Integer.", e);
        }
    }

    @Override
    public short getShort(int parameterOrder) throws InvalidDataSetException {
        try {
            return (Short)mParameterProvider.getParameter(parameterOrder);
        } catch (ClassCastException e) {
            throw new InvalidDataSetException("Parameter " + parameterOrder + " can't be casted to Integer.", e);
        }
    }

    @Override
    public void setStatement(ParameterProvider parameterProvider) {
        this.mParameterProvider = parameterProvider;
    }
}
