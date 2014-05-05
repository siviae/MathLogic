package ru.ifmo.ctddev.isaev.structure;

import javafx.util.Pair;
import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
import ru.ifmo.ctddev.isaev.exception.TreeMismatchException;
import ru.ifmo.ctddev.isaev.structure.logic.Variable;
import ru.ifmo.ctddev.isaev.structure.predicate.Quantifier;
import ru.ifmo.ctddev.isaev.structure.predicate.Term;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    Set<String> getFreeVars();

    boolean hasQuantifier(Variable var);

    void setQuantifiers(Map<String, Quantifier> quantifiers);

    int markFreeVariableOccurences(String variableName);

    Set<Pair<Term, Term>> getReplacedVariableOccurences(Expression originalExpr) throws TreeMismatchException;

}
