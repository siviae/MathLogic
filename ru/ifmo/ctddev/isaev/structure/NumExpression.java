package ru.ifmo.ctddev.isaev.structure;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 06.11.13
 * Time: 13:35
 * To change this template use File | Settings | File Templates.
 */
public class NumExpression implements Expression {
    public int number;

    public NumExpression(int number) {
        this.number = number;
    }

    @Override
    public boolean match(Expression other) {
        return true;
    }

    @Override
    public boolean matchAxiomScheme(Expression expr, HashMap<Integer, Expression> known) {
        if (known.containsKey(number)) {
            return known.get(number).match(expr);
        } else {
            known.put(number, expr);
            return true;
        }
    }

    @Override
    public Expression substitute(HashMap<String, Expression> variables) {
        return null;
    }

    @Override
    public boolean hasSameType(Expression other) {
        return true;
    }

    @Override
    public StringBuilder asString() {
        return new StringBuilder(number).append("}").insert(0, "{");
    }

}
