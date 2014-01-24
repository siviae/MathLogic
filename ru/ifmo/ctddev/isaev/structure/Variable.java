package ru.ifmo.ctddev.isaev.structure;

import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 05.11.13
 * Time: 20:34
 * To change this template use File | Settings | File Templates.
 */
public class Variable extends AbstractExpression {
    public String token;
    public boolean currentValue;

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
    public Expression substitute(HashMap<String, ? extends Expression> variables) {
        return variables.containsKey(token) ? variables.get(token) : new Variable(token);
    }

    @Override
    public boolean hasSameType(Expression other) {
        return other instanceof Variable;
    }

    @Override
    public boolean evaluate() {
        return currentValue;
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
    public List<Expression> getParticularProof(List<? extends Expression> hypos) throws ProofGeneratingException {
        return new LinkedList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Variable)) return false;

        Variable variable = (Variable) o;

        if (currentValue != variable.currentValue) return false;
        if (!token.equals(variable.token)) return false;

        return true;
    }

    @Override
    public HashMap<String, Variable> getVars() {
        HashMap<String, Variable> vars = new HashMap<>();
        vars.put(token, this);
        return vars;
    }

    @Override
    public int hashCode() {
        int result = token.hashCode();
        result = 31 * result + (currentValue ? 1 : 0);
        return result;
    }
}
