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
 * Time: 20:36
 * To change this template use File | Settings | File Templates.
 */
public abstract class Unary extends AbstractExpression {
    protected Expression operand;
    protected Lexeme token;

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
    public Pair<Boolean, Variable> findSubstitutionAndCheck(Expression other, Variable original, Variable alreadyKnown) throws SubstitutionException {
        if (!hasSameType(other)) throw new SubstitutionException();
        return operand.findSubstitutionAndCheck(other, original, alreadyKnown);
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
