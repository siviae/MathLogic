package ru.ifmo.ctddev.isaev.exception;

/**
 * User: Xottab
 * Date: 07.04.14
 */
public class SubstitutionException extends Exception {
    public SubstitutionException() {
        super("Substitution error");
    }

    public SubstitutionException(String message) {
        super(message);
    }
}
