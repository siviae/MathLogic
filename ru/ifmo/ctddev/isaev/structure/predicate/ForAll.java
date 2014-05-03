package ru.ifmo.ctddev.isaev.structure.predicate;

import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
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
public class ForAll extends Unary {

    public Lexeme token = Lexeme.FOR_ALL;
    public Term var;


    public ForAll(Expression operand) {
        super(operand);
    }

    public ForAll(Term var, Expression operand) {
        super(operand);
        this.var = var;
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
    public Map<String, Variable> getFreeVars() {
        Map<String, Variable> vars = getVars();
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
    public boolean hasQuantifier(Variable var) {
        return !(var.getName().equals(this.var.getName())) && !operand.hasQuantifier(var);
    }



    /*@Override
    public Pair<Boolean, Term> findSubstitutionAndCheck2(Expression other, Variable original, Term alreadyKnown) throws SubstitutionException {
        if (!hasSameType(other)) throw new SubstitutionException();
        Pair<Boolean, Term> result = operand.findSubstitutionAndCheck(((ForAll)other).operand, original, alreadyKnown);
        Term t = result.getValue();
        if (t!=null && t instanceof Variable && var.name.equals(t.name)) {
            return new Pair<>(false, var);
        } else return result;
    }*/

}
