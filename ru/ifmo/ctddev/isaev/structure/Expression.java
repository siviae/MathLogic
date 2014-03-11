package ru.ifmo.ctddev.isaev.structure;

import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 05.11.13
 * Time: 20:32
 * To change this template use File | Settings | File Templates.
 */
public interface Expression {

    boolean match(Expression other);

    boolean matchAxiomScheme(Expression expr, HashMap<Integer, Expression> known);

    Expression substituteAndCopy(Map<String, ? extends Expression> variables);

    Expression substitute(Map<String, ? extends Expression> variables);

    boolean hasSameType(Expression other);

    boolean evaluate();

    StringBuilder asString();

    StringBuilder asJavaExpr();

    List<Expression> getParticularProof(List<? extends Expression> hypos) throws ProofGeneratingException;

    HashMap<String, Variable> getVars();

    boolean canSubstitute(Variable var);
}
