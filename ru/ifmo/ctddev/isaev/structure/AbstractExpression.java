package ru.ifmo.ctddev.isaev.structure;

import ru.ifmo.ctddev.isaev.parser.Lexeme;

/**
 * User: Xottab
 * Date: 29.11.13
 */
public abstract class AbstractExpression implements Expression {


    protected Lexeme token;

    @Override
    public String toString() {
        return asString().toString();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof AbstractExpression
                && this.toString().equals(o.toString());
    }

    public boolean hasSameType(Expression other) {
        return this.getClass().getSimpleName().equals(other.getClass().getSimpleName());
    }

    public boolean match(Expression other) {
        return this.toString().equals(other.toString());
    }
}
