package com.mnw.dataset;

public class PassResult implements Result {
    @Override
    public boolean isAssertionFailure() {
        return false;
    }

    @Override
    public boolean isSkipped() {
        return false;
    }

    @Override
    public boolean isSerious() {
        return false;
    }
}
