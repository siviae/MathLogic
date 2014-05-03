package ru.ifmo.ctddev.isaev.structure;

import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
import ru.ifmo.ctddev.isaev.structure.logic.Variable;

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

    boolean treeEquals(Expression other);

    boolean match(Expression other);

    boolean matchAxiomScheme(Expression expr, Map<Integer, Expression> known);

    Expression substituteAndCopy(Map<String, ? extends Expression> variables);

    Expression substitute(Map<String, ? extends Expression> variables);

    boolean hasSameType(Expression other);

    boolean evaluate();

    StringBuilder asString();

    StringBuilder asJavaExpr();

    List<Expression> getParticularProof(List<? extends Expression> hypos) throws ProofGeneratingException;

    Map<String, Variable> getVars();

    Map<String, Variable> getFreeVars();

    boolean hasQuantifier(Variable var);

    /**
     * @param original search the variable, substituted instead of original, checks if it is equal to alreadyKnown value
     *                 returns pair
     *                 first parameter is true if substitution is correct
     *                 second parameter is not null if we finded some variable substituted
     *                 default - false,null
     *                 finded some correct - true, not null
     *                 finded some crap - false, not null
     * @param alreadyKnown
     */
    // Pair<Boolean, Term> findSubstitutionAndCheck(Expression other, Variable original, Term alreadyKnown) throws SubstitutionException;

}
