package ru.ifmo.ctddev.isaev.structure;

import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Xottab
 * Date: 17.12.13
 */
public class Predicate extends Unary {
    String name;
    public Term[] arguments;

    public Predicate(Expression operand) {
        super(operand);
    }

    public Predicate(String name) {
        super();
        this.name = name;
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
    public Expression substituteAndCopy(Map<String, ? extends Expression> variables) {
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
    public List<Expression> getParticularProof(List<? extends Expression> hypos) throws ProofGeneratingException {
        return null;
    }
}
