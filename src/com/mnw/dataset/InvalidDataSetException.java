package com.mnw.dataset;

public class InvalidDataSetException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -5035084302374443080L;

    public InvalidDataSetException(String string) {
        super(string);
    }

    public InvalidDataSetException(String string, InstantiationException ie) {
        super(string, ie);
    }

}
