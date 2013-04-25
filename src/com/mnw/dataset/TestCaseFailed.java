package com.mnw.dataset;

/** TODO description of this class is missing */
public class TestCaseFailed extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -5035084302374213430L;

    public TestCaseFailed(String string) {
        super(string);
    }

    public TestCaseFailed(String description, Throwable originalThrowable) {
        super(description, originalThrowable);
        setStackTrace(new StackTraceElement[0]);
    }
}
