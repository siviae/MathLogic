package ru.ifmo.ctddev.isaev.exception;

/**
 * User: Xottab
 * Date: 01.12.13
 */
public class IncorrectProofException extends Exception {
    public IncorrectProofException(String s) {
        super("Cant generate proof for "+s);
    }
}
