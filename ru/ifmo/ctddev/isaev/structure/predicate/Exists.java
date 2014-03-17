package ru.ifmo.ctddev.isaev.structure.predicate;

import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
import ru.ifmo.ctddev.isaev.parser.Lexeme;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.Unary;
import ru.ifmo.ctddev.isaev.structure.Variable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Xottab
 * Date: 17.12.13
 */
public class Exists extends Unary {
    public Lexeme token = Lexeme.EXISTS;
    public Variable var;

    public Exists(Expression operand) {
        super(operand);
    }

    public Exists(Variable var, Expression operand) {
        super(operand);
        this.var = var;
    }


    @Override
    public boolean match(Expression other) {
        return hasSameType(other)
                && var.match(other)
                && operand.match(other);
    }

    @Override
    public boolean matchAxiomScheme(Expression expr, HashMap<Integer, Expression> known) {
        return false;
    }

    @Override
    public Expression substituteAndCopy(Map<String, ? extends Expression> variables) {
        return null;
    }

    /*@Override
    public boolean hasSameType(Expression other) {
        return false;
    }*/

    @Override
    public boolean evaluate() {
        return false;
    }

    @Override
    public StringBuilder asString() {
        return new StringBuilder(token.s).append(var.asString()).append("(").append(operand.asString()).append(")");
    }

    @Override
    public StringBuilder asJavaExpr() {
        return null;
    }

    @Override
    public List<Expression> getParticularProof(List<? extends Expression> hypos) throws ProofGeneratingException {
        return null;
    }

    @Override
    public boolean canSubstitute(Variable var) {
        return !(var.getName().equals(this.var.getName())) && !operand.canSubstitute(var);
    }
}
