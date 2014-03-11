package ru.ifmo.ctddev.isaev.structure;

/**
 * User: Xottab
 * Date: 29.11.13
 */
public abstract class AbstractExpression implements Expression {
    @Override
    public String toString() {
        return asString().toString();
    }

    public boolean hasSameType(Expression other) {
        return this.getClass().getSimpleName().equals(other.getClass().getSimpleName());
    }
}
