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
 * Time: 20:35
 * To change this template use File | Settings | File Templates.
 */
public abstract class Binary extends AbstractExpression {
    protected Expression left;
    protected Expression right;

    protected Binary(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Lexeme getToken() {
        return token;
    }

    public Expression getRight() {
        return right;
    }

    public Expression getLeft() {
        return left;
    }

    @Override
    public boolean matchAxiomScheme(Expression axiomScheme, Map<Integer, Expression> known) {
        return hasSameType(axiomScheme)
                && left.matchAxiomScheme(((Binary) axiomScheme).left, known)
                && right.matchAxiomScheme(((Binary) axiomScheme).right, known);
    }

    @Override
    public StringBuilder asString() {
        StringBuilder s = left.asString();
        StringBuilder s2 = right.asString();
        if (left instanceof Binary) {
            s.insert(0, Lexeme.LEFT_P.s);
            s.append(Lexeme.RIGHT_P.s);
        }
        if (right instanceof Binary) {
            s2.insert(0, Lexeme.LEFT_P.s);
            s2.append(Lexeme.RIGHT_P.s);
        }
        return s.append(token.s).append(s2)/*.append(Lexeme.RIGHT_P.s).insert(0,Lexeme.LEFT_P.s)*/;
    }

    @Override
    public Map<String, Variable> getVars() {
        Map<String, Variable> h = left.getVars();
        h.putAll(right.getVars());
        return h;
    }

    @Override
    public Set<String> getFreeVars() {
        Set<String> h = left.getFreeVars();
        h.addAll(right.getFreeVars());
        return h;
    }

    @Override
    public StringBuilder asJavaExpr() {
        return new StringBuilder("new ").append(getClass().getSimpleName()).append("(").append(left.asJavaExpr()).append(",").append(right.asJavaExpr()).append(")");
    }

    @Override
    public boolean treeEquals(Expression other) {
        return hasSameType(other)
                && ((Binary) other).left.treeEquals(left)
                && ((Binary) other).right.treeEquals(right);
    }

    @Override
    public boolean hasQuantifier(Variable v) {
        return left.hasQuantifier(v) && right.hasQuantifier(v);
    }

    @Override
    public List<Expression> getParticularProof(List<? extends Expression> hypos) throws ProofGeneratingException {
        List<Expression> result = left.getParticularProof(hypos);
        result.addAll(right.getParticularProof(hypos));
        return result;
    }

    @Override
    public void setQuantifiers(Map<String, Quantifier> quantifiers) {
        left.setQuantifiers(quantifiers);
        right.setQuantifiers(quantifiers);
    }

    @Override
    public int markFreeVariableOccurences(String variableName) {
        int result = left.markFreeVariableOccurences(variableName);
        result += right.markFreeVariableOccurences(variableName);
        return result;
    }

    @Override
    public Set<Pair<Term, Term>> getReplacedVariableOccurences(Expression originalExpr) throws TreeMismatchException {
        if (!hasSameType(originalExpr)) throw new TreeMismatchException(originalExpr, this);
        Set<Pair<Term, Term>> set = left.getReplacedVariableOccurences(((Binary) originalExpr).left);
        set.addAll(right.getReplacedVariableOccurences(((Binary) originalExpr).right));
        return set;
    }

    @Override
    public Expression substitute(Map<String, ? extends Expression> variables) {
        left = left.substitute(variables);
        right = right.substitute(variables);
        return this;
    }
}
