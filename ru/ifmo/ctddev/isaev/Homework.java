package ru.ifmo.ctddev.isaev;

import ru.ifmo.ctddev.isaev.exception.IncorrectProofException;
import ru.ifmo.ctddev.isaev.exception.LexingException;
import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.Then;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 10.11.13
 * Time: 17:44
 * To change this template use File | Settings | File Templates.
 */
public abstract class Homework {
    protected int row;


    public abstract void doSomething() throws IOException, ParsingException, LexingException, IncorrectProofException;

    public static boolean modusPonens(Expression A, Expression aThenB, Expression B) {
        if (aThenB instanceof Then) {
            return A.match(((Then) aThenB).left)
                    && B.match(((Then) aThenB).right);
        } else {
            return false;
        }
    }


}
