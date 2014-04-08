package ru.ifmo.ctddev.isaev.structure.predicate;

import javafx.util.Pair;
import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
import ru.ifmo.ctddev.isaev.exception.SubstitutionException;
import ru.ifmo.ctddev.isaev.parser.Lexeme;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.logic.Unary;
import ru.ifmo.ctddev.isaev.structure.logic.Variable;

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
    public boolean treeEquals(Expression other) {
        return hasSameType(other)
                && var.treeEquals(((Exists) other).var)
                && operand.treeEquals(((Exists) other).operand);
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
    public Pair<Boolean, Variable> findSubstitutionAndCheck(Expression other, Variable original, Variable alreadyKnown) throws SubstitutionException {
        if (!hasSameType(other)) throw new SubstitutionException();
        Pair<Boolean, Variable> result = operand.findSubstitutionAndCheck(((Exists) other).operand, original, alreadyKnown);
        if (var.name.equals(original.name)) {
            return new Pair<>(false, result.getValue());
        } else {
            return result;
        }
    }

    @Override
    public boolean evaluate() {
        return false;
    }

    @Override
    public StringBuilder asString() {
        return new StringBuilder(token.s).append(var.asString()).append("(").append(operand.asString()).append(")");
    }

    @Override
    public Map<String, Variable> getFreeVars() {
        Map<String, Variable> vars = getVars();
        vars.remove(var.getName());
        return vars;
    }

    @Override
    public StringBuilder asJavaExpr() {
        return new StringBuilder("new Exists(").append(var.asJavaExpr()).append(",").append(operand.asJavaExpr()).append(")");
    }

    @Override
    public List<Expression> getParticularProof(List<? extends Expression> hypos) throws ProofGeneratingException {
        return null;
    }

    @Override
    public boolean hasQuantifier(Variable var) {
        return !(var.getName().equals(this.var.getName())) && !operand.hasQuantifier(var);
    }
}
