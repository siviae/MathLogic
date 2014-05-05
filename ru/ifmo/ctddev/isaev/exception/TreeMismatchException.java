package ru.ifmo.ctddev.isaev.exception;

import ru.ifmo.ctddev.isaev.structure.Expression;

/**
 * User: Xottab
 * Date: 05.05.14
 */
public class TreeMismatchException extends Exception {
    public TreeMismatchException(Expression original, Expression other) {
        super("expected " + original + ", founded " + other);
    }
}
