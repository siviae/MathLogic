package ru.ifmo.ctddev.isaev;

import ru.ifmo.ctddev.isaev.exception.IncorrectProofException;
import ru.ifmo.ctddev.isaev.exception.LexingException;
import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.logic.Then;
import ru.ifmo.ctddev.isaev.structure.predicate.Exists;
import ru.ifmo.ctddev.isaev.structure.predicate.ForAll;

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

    public static boolean modusPonens(Expression A, Expression aThenB, Expression B) {
        return aThenB instanceof Then && A.match(((Then) aThenB).getLeft()) && B.match(((Then) aThenB).getRight());
    }

    public static boolean forAllRule(Expression A, Expression B) {
        return A instanceof Then
                && B instanceof Then
                && ((Then) A).getRight().match(((Then) B).getRight())
                && ((Then) A).getRight().canSubstitute(((ForAll) (((Then) B).getRight())).var)
                && ((Then) A).getRight().match(((ForAll) (((Then) B).getRight())).getOperand());
    }

    public static boolean existsRule(Expression A, Expression B) {
        return A instanceof Then
                && B instanceof Then
                && ((Then) A).getLeft().match(((Then) B).getLeft())
                && ((Then) A).getLeft().canSubstitute(((Exists) (((Then) B).getLeft())).var)
                && ((Then) A).getLeft().match(((Exists) (((Then) B).getLeft())).getOperand());
    }

    public abstract void doSomething() throws IOException, ParsingException, LexingException, IncorrectProofException, ProofGeneratingException;

}
