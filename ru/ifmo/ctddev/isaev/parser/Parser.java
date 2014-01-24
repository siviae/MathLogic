package ru.ifmo.ctddev.isaev.parser;

import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.structure.Expression;

/**
 * User: Xottab
 * Date: 22.01.14
 */
public abstract class Parser {

    protected String[] tokens;
    protected int position = 0;

    public Expression parse(String[] tokens) throws ParsingException {
        this.tokens = tokens;
        this.position = 0;
        return expr();
    }

    protected abstract Expression expr() throws ParsingException;
}
