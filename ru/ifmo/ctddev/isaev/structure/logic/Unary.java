package ru.ifmo.ctddev.isaev.structure.logic;

import javafx.util.Pair;
import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
import ru.ifmo.ctddev.isaev.exception.TreeMismatchException;
import ru.ifmo.ctddev.isaev.parser.Lexeme;
import ru.ifmo.ctddev.isaev.structure.AbstractExpression;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.predicate.Quantifier;
import ru.ifmo.ctddev.isaev.structure.predicate.Term;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 05.11.13
 * Time: 20:36
 * To change this template use File | Settings | File Templates.
 */
public abstract class Unary extends AbstractExpression {
    protected Expression operand;

    public Unary(Expression operand) {
        this.operand = operand;
    }

    public Expression getOperand() {
        return operand;
    }

    public Lexeme getToken() {
        return token;
    }

    @Override
    public boolean matchAxiomScheme(Expression expr, Map<Integer, Expression> known) {
        return hasSameType(expr)
                && operand.matchAxiomScheme(((Unary) expr).operand, known);
    }

    @Override
    public StringBuilder asString() {
        StringBuilder s = operand.asString();
        if (operand instanceof Binary) {
            s.insert(0, Lexeme.LEFT_P.s);
            s.append(Lexeme.RIGHT_P.s);
        }
        return s.insert(0, token.s);
    }

    @Override
    public StringBuilder asJavaExpr() {
        return new StringBuilder("new ").append(getClass().getSimpleName()).append("(").append(operand.asJavaExpr()).append(")");
    }

    @Override
    public Map<String, Variable> getVars() {
        return operand.getVars();
    }

    @Override
    public Set<String> getFreeVars() {
        return operand.getFreeVars();
    }

    @Override
    public void setQuantifiers(Map<String, Quantifier> quantifiers) {
        operand.setQuantifiers(quantifiers);
    }

    @Override
    public int markFreeVariableOccurences(String variableName) {
        return operand.markFreeVariableOccurences(variableName);
    }

    @Override
    public Set<Pair<Term, Term>> getReplacedVariableOccurences(Expression originalExpr) throws TreeMismatchException {
        if (!hasSameType(originalExpr))
            throw new TreeMismatchException(originalExpr, this);
        return operand.getReplacedVariableOccurences(((Unary) originalExpr).operand);
    }

    @Override
    public List<Expression> getParticularProof(List<? extends Expression> hypos) throws ProofGeneratingException {
        return operand.getParticularProof(hypos);
    }

    @Override
    public Expression substitute(Map<String, ? extends Expression> variables) {
        operand = operand.substitute(variables);
        return this;
    }
}
