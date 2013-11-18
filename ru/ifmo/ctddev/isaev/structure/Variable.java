package ru.ifmo.ctddev.isaev.structure;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 05.11.13
 * Time: 20:34
 * To change this template use File | Settings | File Templates.
 */
public class Variable implements Expression {
    public String token;
    public boolean value;

    public Variable(String token) {
        this.token = token;
    }

    @Override
    public boolean match(Expression other) {
        return hasSameType(other) && (token != null && ((Variable) other).token.equals(token));
    }

    @Override
    public boolean matchAxiomScheme(Expression expr, HashMap<Integer, Expression> known) {
        return false;
    }

    @Override
    public Expression substitute(HashMap<String, Expression> variables) {
        return variables.containsKey(token) ? variables.get(token) : new Variable(token);
    }

    @Override
    public boolean hasSameType(Expression other) {
        return other instanceof Variable;
    }

    @Override
    public boolean evaluate() {
        return value;
    }

    @Override
    public StringBuilder asString() {
        return new StringBuilder(token);
    }


}
