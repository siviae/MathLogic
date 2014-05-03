package ru.ifmo.ctddev.isaev.structure.logic;

import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
import ru.ifmo.ctddev.isaev.structure.AbstractExpression;
import ru.ifmo.ctddev.isaev.structure.Expression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 06.11.13
 * Time: 13:35
 * To change this template use File | Settings | File Templates.
 */
public class NumExpr extends AbstractExpression {
    public int number;

    public NumExpr(int number) {
        this.number = number;
    }

    @Override
    public boolean treeEquals(Expression other) {
        return true;
    }

    @Override
    public boolean matchAxiomScheme(Expression expr, Map<Integer, Expression> known) {
        if (known.containsKey(number)) {
            return known.get(number).treeEquals(expr);
        } else {
            known.put(number, expr);
            return true;
        }
    }

    @Override
    public Expression substituteAndCopy(Map<String, ? extends Expression> variables) {
        return variables.containsKey(Integer.toString(number)) ? variables.get(Integer.toString(number)) : this;
    }

    @Override
    public Expression substitute(Map<String, ? extends Expression> variables) {
        return substituteAndCopy(variables);
    }

    @Override
    public boolean hasSameType(Expression other) {
        return true;
    }

    @Override
    public boolean evaluate() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StringBuilder asString() {
        return new StringBuilder(number).append("}").insert(0, "{");
    }

    @Override
    public StringBuilder asJavaExpr() {
        return new StringBuilder("new NumExpr(").append(number).append(")");
    }

    @Override
    public List<Expression> getParticularProof(List<? extends Expression> hypos) throws ProofGeneratingException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public HashMap<String, Variable> getVars() {
        return null;
    }

    @Override
    public boolean hasQuantifier(Variable var) {
        return true;
    }


}
