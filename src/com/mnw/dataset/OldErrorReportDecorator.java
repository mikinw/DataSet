package com.mnw.dataset;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/** TODO description of this class is missing */
public class OldErrorReportDecorator { //implements ErrorReportDecorator {

    /**
     * Tries to construct an exception with the same class as the given parameter, but with the
     * information at which dataset row did the exception happen. If it fails for some (probably
     * reflection) reason, it creates a general Exception on top of the exception stack. The message
     * of the original exception is copied to the new exception.
     * @param originalThrowable The exception that needs to wrapped around.
     * @param hint is appended to the message
     * @return the generated general or specific exception
     */
//    @Override
    @SuppressWarnings("MagicCharacter")
    public Throwable decorate(Throwable originalThrowable, int testCaseNo, String hint) {
        Throwable additionalThrowable = null;
        StringBuilder message = new StringBuilder(100);
        message.append("In DataSet row ");
        message.append(testCaseNo);
        try {
            @SuppressWarnings("unchecked")
            Class<Throwable> additionalThrowableClass = (Class<Throwable>) originalThrowable.getClass();
            Constructor<Throwable> constructor = null;
            try {
                constructor = additionalThrowableClass.getConstructor(new Class[] { String.class, Throwable.class });
                additionalThrowable = constructor.newInstance(message, originalThrowable);
            } catch (NoSuchMethodException nsme) { /* fallback to general constructor in this case */ }
            message.append(". occurred: ");
            message.append(originalThrowable.getMessage());
            message.append(System.getProperty("line.separator"));
            message.append(hint);

            if (constructor == null) {
                try {
                    constructor = additionalThrowableClass.getConstructor(new Class[] { String.class });
                    additionalThrowable = constructor.newInstance(originalThrowable.getClass().toString() + ":  " + message);
                } catch (NoSuchMethodException nsme) { /* fallback to general Exception in this case */ }
            }
            if (additionalThrowable == null) {
                StringBuilder additionalMessage = new StringBuilder(hint.length() + message.length() + 100);
                additionalMessage.append('(')
                        .append(additionalThrowableClass.toString())
                        .append(") ").append(message).append(' ').append(hint);
                additionalThrowable = new Throwable(additionalMessage.toString(), originalThrowable);
            }
        } catch (SecurityException se) {
            se.printStackTrace();
            additionalThrowable = new Exception(message + " " + hint, originalThrowable);
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
            additionalThrowable = new Exception(message + " " + hint, originalThrowable);
        } catch (InstantiationException ie) {
            ie.printStackTrace();
            additionalThrowable = new Exception(message + " " + hint, originalThrowable);
        } catch (IllegalAccessException iae) {
            iae.printStackTrace();
            additionalThrowable = new Exception(message + " " + hint, originalThrowable);
        } catch (InvocationTargetException ite) {
            ite.printStackTrace();
            additionalThrowable = new Exception(message + " " + hint, originalThrowable);
        }

        return additionalThrowable;
    }
}
