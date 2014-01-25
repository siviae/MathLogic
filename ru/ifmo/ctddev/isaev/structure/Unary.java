package ru.ifmo.ctddev.isaev.structure;

import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
import ru.ifmo.ctddev.isaev.parser.Lexeme;

import java.util.HashMap;
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
    public Expression operand;
    public Lexeme token;

    public Unary(Expression operand) {
        this.operand = operand;
    }

    public Unary() {
    }

    @Override
    public boolean matchAxiomScheme(Expression expr, HashMap<Integer, Expression> known) {
        return hasSameType(expr)
                && operand.matchAxiomScheme(((Unary) expr).operand, known);
    }

    @Override
    public StringBuilder asString() {
        StringBuilder s = operand.asString();
       if (operand instanceof Binary) {
            s.insert(0, Lexeme.LEFT_P.token);
            s.append(Lexeme.RIGHT_P.token);
        }
        return s.insert(0, token.token);
    }

    @Override
    public StringBuilder asJavaExpr() {
        return new StringBuilder("new ").append(getClass().getSimpleName()).append("(").append(operand.asJavaExpr()).append(")");
    }

    @Override
    public HashMap<String, Variable> getVars() {
        return operand.getVars();
    }

    @Override
    public List<Expression> getParticularProof(List<? extends Expression> hypos) throws ProofGeneratingException {
        return operand.getParticularProof(hypos);
    }
    @Override
    public Expression substitute(Map<String, ? extends Expression> variables) {
        operand=operand.substitute(variables);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Unary)) return false;
        Unary that = (Unary) o;
        return operand.equals(that.operand) && token == that.token;

    }

    @Override
    public int hashCode() {
        int result = operand.hashCode();
        result = 31 * result + token.hashCode();
        return result;
    }
}
