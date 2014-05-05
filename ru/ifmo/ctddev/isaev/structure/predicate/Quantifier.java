package ru.ifmo.ctddev.isaev.structure.predicate;

import javafx.util.Pair;
import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
import ru.ifmo.ctddev.isaev.exception.TreeMismatchException;
import ru.ifmo.ctddev.isaev.structure.AbstractExpression;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.logic.Variable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: Xottab
 * Date: 03.05.14
 */
public class Quantifier extends AbstractExpression {

    public Term var;
    public Expression operand;

    public Quantifier(Term var, Expression predicate) {
        this.var = var;
        this.operand = predicate;
    }

    @Override
    public boolean treeEquals(Expression other) {
        return hasSameType(other)
                && var.treeEquals(((ForAll) other).var)
                && operand.treeEquals(((ForAll) other).operand);
    }

    @Override
    public boolean matchAxiomScheme(Expression expr, Map<Integer, Expression> known) {
        return false;
    }

    @Override
    public Expression substituteAndCopy(Map<String, ? extends Expression> variables) {
        return null;
    }

    @Override
    public Expression substitute(Map<String, ? extends Expression> variables) {
        return null;
    }

    @Override
    public Set<String> getFreeVars() {
        Set<String> vars = operand.getFreeVars();
        vars.remove(var.getName());
        return vars;
    }

    @Override
    public boolean evaluate() {
        return false;
    }

    @Override
    public StringBuilder asString() {
        return new StringBuilder(token.s).append(var.toString()).append("(").append(operand.toString()).append(")");
    }

    @Override
    public StringBuilder asJavaExpr() {
        return new StringBuilder("new ForAll(").append(var.asJavaExpr()).append(",").append(operand.asJavaExpr()).append(")");
    }

    @Override
    public List<Expression> getParticularProof(List<? extends Expression> hypos) throws ProofGeneratingException {
        return null;
    }

    @Override
    public Map<String, Variable> getVars() {
        return null;
    }

    @Override
    public void setQuantifiers(Set<String> quantifiers) {
        boolean f = quantifiers.contains(var.name);
        quantifiers.add(var.name);
        operand.setQuantifiers(quantifiers);
        if (!f) quantifiers.remove(var.name);
    }

    @Override
    public int markFreeVariableOccurences(String variableName) {
        return operand.markFreeVariableOccurences(variableName);
    }

    @Override
    public Set<Pair<Term, Term>> getReplacedVariableOccurences(Expression originalExpr) throws TreeMismatchException {
        if (!hasSameType(originalExpr) || !this.var.name.equals(((Quantifier) originalExpr).var.name))
            throw new TreeMismatchException(originalExpr, this);
        return operand.getReplacedVariableOccurences(((Quantifier) originalExpr).operand);
    }

    public Expression getOperand() {
        return operand;
    }
}
