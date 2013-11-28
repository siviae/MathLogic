package ru.ifmo.ctddev.isaev;

import ru.ifmo.ctddev.isaev.exception.LexingException;
import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.parser.Lexer;
import ru.ifmo.ctddev.isaev.parser.Parser;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.LogicalThen;

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
    public  BufferedReader in;
    public  PrintWriter out;
    protected static Lexer lexer = new Lexer();
    protected static Parser parser = new Parser();
    protected  int row;



    public static Expression parse(String s) throws LexingException, ParsingException {
        String[] lexems = lexer.lex(s);
        return parser.parse(lexems);
    }

    public abstract void doSomething() throws IOException, ParsingException, LexingException;

    public static boolean modusPonens(Expression A, Expression aThenB, Expression B) {
        if (aThenB instanceof LogicalThen) {
            return A.match(((LogicalThen) aThenB).left)
                    && B.match(((LogicalThen) aThenB).right);
        } else {
            return false;
        }
    }


}
