package ru.ifmo.ctddev.isaev;

import ru.ifmo.ctddev.isaev.exception.IncorrectProofException;
import ru.ifmo.ctddev.isaev.exception.LexingException;
import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.parser.Lexer;
import ru.ifmo.ctddev.isaev.parser.Parser;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.LogicalThen;

import static ru.ifmo.ctddev.isaev.General.in;
import static ru.ifmo.ctddev.isaev.General.out;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 10.11.13
 * Time: 17:44
 * To change this template use File | Settings | File Templates.
 */
public abstract class Homework {
    protected  int row;




    public abstract void doSomething() throws IOException, ParsingException, LexingException, IncorrectProofException;

    public static boolean modusPonens(Expression A, Expression aThenB, Expression B) {
        if (aThenB instanceof LogicalThen) {
            return A.match(((LogicalThen) aThenB).left)
                    && B.match(((LogicalThen) aThenB).right);
        } else {
            return false;
        }
    }


}
