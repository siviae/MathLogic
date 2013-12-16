package ru.ifmo.ctddev.isaev.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 05.11.13
 * Time: 20:34
 * To change this template use File | Settings | File Templates.
 */
public class Variable extends AbstractExpression{
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

    @Override
    public StringBuilder asJavaExpr() {
        return new StringBuilder("new Variable(\"").append(token).append("\")");
    }

    @Override
    public List<Expression> getParticularProof(ArrayList<Expression> hypos) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String toString() {
        return asString().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Variable)) return false;

        Variable variable = (Variable) o;

        if (value != variable.value) return false;
        if (!token.equals(variable.token)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = token.hashCode();
        result = 31 * result + (value ? 1 : 0);
        return result;
    }
}
