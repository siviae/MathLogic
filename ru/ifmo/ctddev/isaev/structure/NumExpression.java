package ru.ifmo.ctddev.isaev.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 06.11.13
 * Time: 13:35
 * To change this template use File | Settings | File Templates.
 */
public class NumExpression extends AbstractExpression {
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
        return variables.containsKey(Integer.toString(number)) ? variables.get(Integer.toString(number)) : this;
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
        return new StringBuilder("new NumExpression(").append(number).append(")");
    }

    @Override
    public List<Expression> getParticularProof(ArrayList<Expression> hypos) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
