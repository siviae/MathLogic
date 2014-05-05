package ru.ifmo.ctddev.isaev.structure;

import javafx.util.Pair;
import ru.ifmo.ctddev.isaev.exception.TreeMismatchException;
import ru.ifmo.ctddev.isaev.parser.Lexeme;
import ru.ifmo.ctddev.isaev.structure.predicate.Term;

import java.util.HashSet;
import java.util.Set;

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
    public Set<String> getFreeVars() {
        return new HashSet<>();
    }

    @Override
    public Set<Pair<Term, Term>> getReplacedVariableOccurences(Expression originalExpr) throws TreeMismatchException {
        return new HashSet<>();
    }

    @Override
    public int markFreeVariableOccurences(String variableName) {
        return 0;
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
