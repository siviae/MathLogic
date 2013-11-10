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
    FORALL("@"),
    LEFT_P("("),
    RIGHT_P(")");

    public final String token;

    private Lexeme(final String token) {
        this.token = token;
    }
}