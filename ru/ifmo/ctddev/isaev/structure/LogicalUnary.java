package ru.ifmo.ctddev.isaev.structure;

import ru.ifmo.ctddev.isaev.parser.Lexeme;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 05.11.13
 * Time: 20:36
 * To change this template use File | Settings | File Templates.
 */
public abstract class LogicalUnary extends AbstractExpression {
    public Expression operand;
    public Lexeme token;

    public LogicalUnary(Expression operand) {
        this.operand = operand;
    }

    @Override
    public boolean matchAxiomScheme(Expression expr, HashMap<Integer, Expression> known) {
        return hasSameType(expr)
                && operand.matchAxiomScheme(((LogicalUnary) expr).operand, known);
    }

    @Override
    public StringBuilder asString() {
        return operand.asString().append(Lexeme.RIGHT_P.token).insert(0, Lexeme.LEFT_P.token).insert(0, token.token);
    }

    @Override
    public StringBuilder asJavaExpr() {
        return new StringBuilder("new ").append(getClass().getSimpleName()).append("(").append(operand.asJavaExpr()).append(")");
    }

    @Override
    public String toString() {
        return asString().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogicalUnary)) return false;

        LogicalUnary that = (LogicalUnary) o;

        if (!operand.equals(that.operand)) return false;
        if (token != that.token) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = operand.hashCode();
        result = 31 * result + token.hashCode();
        return result;
    }
}
