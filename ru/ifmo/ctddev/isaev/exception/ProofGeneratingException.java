package ru.ifmo.ctddev.isaev.exception;

/**
 * User: Xottab
 * Date: 22.01.14
 */
public class ProofGeneratingException extends Exception{
    public ProofGeneratingException() {
        super("Cant proof this");
    }
    public ProofGeneratingException(String message) {
        super(message);
    }
}
