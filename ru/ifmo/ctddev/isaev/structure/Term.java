package ru.ifmo.ctddev.isaev.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: Xottab
 * Date: 17.12.13
 */
public class Term extends Variable {
    public Term[] arguments;

    public Term(String token) {
        super(token);
    }

    @Override
    public boolean match(Expression other) {
        return false;
    }

    @Override
    public boolean matchAxiomScheme(Expression expr, HashMap<Integer, Expression> known) {
        return false;
    }

    @Override
    public Expression substitute(HashMap<String, Expression> variables) {
        return null;
    }

    @Override
    public boolean hasSameType(Expression other) {
        return false;
    }

    @Override
    public boolean evaluate() {
        return false;
    }

    @Override
    public StringBuilder asString() {
        return null;
    }

    @Override
    public StringBuilder asJavaExpr() {
        return null;
    }

    @Override
    public List<Expression> getParticularProof(ArrayList<Expression> hypos) {
        return null;
    }
}
