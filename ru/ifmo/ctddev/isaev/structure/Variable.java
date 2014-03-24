package ru.ifmo.ctddev.isaev.structure;

import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
import ru.ifmo.ctddev.isaev.structure.logic.Not;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 05.11.13
 * Time: 20:34
 * To change this template use File | Settings | File Templates.
 */
public class Variable extends Term {
    protected Boolean currentValue;

    public Variable(String name) {
        super(name);
    }

    public Boolean getCurrentValue() {

        return currentValue;
    }

    public void setCurrentValue(Boolean currentValue) {
        this.currentValue = currentValue;
    }

    @Override
    public boolean treeEquals(Expression other) {
        return hasSameType(other) && (name != null && ((Variable) other).name.equals(name));
    }

    @Override
    public boolean match(Expression other) {
        return true;
    }

    @Override
    public boolean matchAxiomScheme(Expression expr, Map<Integer, Expression> known) {
        return false;
    }

    @Override
    public Expression substituteAndCopy(Map<String, ? extends Expression> variables) {
        return variables.containsKey(name) ? variables.get(name) : new Variable(name);
    }

    @Override
    public Expression substitute(Map<String, ? extends Expression> variables) {
        return substituteAndCopy(variables);
    }

    /*@Override
    public boolean hasSameType(Expression other) {
        return other instanceof Variable;
    }
*/
    @Override
    public boolean evaluate() {

        if (currentValue == null) {
            System.out.println("variable is not evaluated");
        }
        return currentValue;
    }

    @Override
    public StringBuilder asString() {
        return new StringBuilder(name);
    }

    @Override
    public StringBuilder asJavaExpr() {
        return new StringBuilder("new Variable(\"").append(name).append("\")");
    }

    @Override
    public List<Expression> getParticularProof(List<? extends Expression> hypos) throws ProofGeneratingException {
        boolean f = false;
        List<Expression> list = new ArrayList<>();
        if (hypos.contains(this)) {
            currentValue = true;
            if (!list.contains(this)) list.add(this);
            f = true;
        }
        if (!f && hypos.contains(new Not(this))) {
            currentValue = false;
            if (!list.contains(new Not(this))) list.add(new Not(this));
            f = true;
        }
        if (!f) {
            throw new ProofGeneratingException("no such variable in hypothesis: " + this.name);
        }
        return list;
    }

    @Override
    public HashMap<String, Variable> getVars() {
        HashMap<String, Variable> vars = new HashMap<>();
        vars.put(name, this);
        return vars;
    }
}
