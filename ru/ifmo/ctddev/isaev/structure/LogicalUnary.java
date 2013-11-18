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
public abstract class LogicalUnary implements Expression {
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
        return operand.asString().append(Lexeme.RIGHT_P.token).insert(0, Lexeme.LEFT_P.token).insert(0,token.token);
    }
}
