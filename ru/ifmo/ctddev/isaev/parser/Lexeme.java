package ru.ifmo.ctddev.isaev.parser;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 05.11.13
 * Time: 20:39
 * To change this template use File | Settings | File Templates.
 */
public enum Lexeme {
    AND("&"),
    OR("|"),
    NOT("!"),
    THEN("->"),
    EXISTS("?"),
    FOR_ALL("@"),
    LEFT_P("("),
    RIGHT_P(")"),
    COMMA(",");
    public final String s;

    private Lexeme(final String s) {
        this.s = s;
    }
}