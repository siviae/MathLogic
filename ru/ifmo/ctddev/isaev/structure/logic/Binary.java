package ru.ifmo.ctddev.isaev.structure.logic;

import javafx.util.Pair;
import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
import ru.ifmo.ctddev.isaev.exception.SubstitutionException;
import ru.ifmo.ctddev.isaev.parser.Lexeme;
import ru.ifmo.ctddev.isaev.structure.AbstractExpression;
import ru.ifmo.ctddev.isaev.structure.Expression;

import java.util.List;
import java.util.Map;

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
    protected Lexeme token;

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
    public Pair<Boolean, Variable> findSubstitutionAndCheck(Expression other, Variable original, Variable alreadyKnown) throws SubstitutionException {
        if (!hasSameType(other)) throw new SubstitutionException();
        Pair<Boolean, Variable> ans = left.findSubstitutionAndCheck(other, original, null);
        if (!ans.getKey() && ans.getValue() != null) return ans;//нашли ошибку
        Pair<Boolean, Variable> ans2 = left.findSubstitutionAndCheck(other, original, ans.getValue());
        if (!ans.getKey() && ans.getValue() != null) return ans;//нашли ошибку
        if (ans2.getKey()) return ans2;
        return new Pair<>(false, null);
    }

    @Override
    public List<Expression> getParticularProof(List<? extends Expression> hypos) throws ProofGeneratingException {
        List<Expression> result = left.getParticularProof(hypos);
        result.addAll(right.getParticularProof(hypos));
        return result;
    }

    @Override
    public Expression substitute(Map<String, ? extends Expression> variables) {
        left = left.substitute(variables);
        right = right.substitute(variables);
        return this;
    }
}
