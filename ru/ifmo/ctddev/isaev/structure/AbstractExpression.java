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

    @Override
    public int hashCode() {
        return asString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof AbstractExpression
                && toString().equals(o.toString());
    }

    public boolean hasSameType(Expression other) {
        return this.getClass().getSimpleName().equals(other.getClass().getSimpleName());
    }
}
