package ru.ifmo.ctddev.isaev.structure;

import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Xottab
 * Date: 17.12.13
 */
public class Predicate extends AbstractExpression {
    protected String name;
    protected Term[] arguments;

    public Predicate(String name) {
        this.arguments = new Term[0];
        this.name = name;
    }

    public Predicate(String name, Term... terms) {
        this.arguments = terms;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Term[] getArguments() {
        return arguments;
    }

    public void setArguments(Term[] arguments) {
        this.arguments = arguments;
    }

    @Override
    public boolean match(Expression other) {
        if (hasSameType(other)) {
            Predicate pred = (Predicate) other;
            if (name.equals(pred.name) && arguments.length == pred.arguments.length) {
                boolean f = false;
                for (int i = 0; i < arguments.length; i++) {
                    if (!arguments[i].match(pred.arguments[i])) {
                        f = true;
                        break;
                    }
                }
                if (!f) return true;
            }
        }
        return false;
    }

    @Override
    public boolean matchAxiomScheme(Expression expr, HashMap<Integer, Expression> known) {
        return false;
    }

    @Override
    public Expression substituteAndCopy(Map<String, ? extends Expression> variables) {
        Predicate pred = new Predicate(this.name);
        Term[] args = new Term[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            args[i] = (Term) arguments[i].substitute(variables);
        }
        pred.setArguments(args);
        return pred;
    }

    @Override
    public Expression substitute(Map<String, ? extends Expression> variables) {
        for (int i = 0; i < arguments.length; i++) {
            arguments[i] = (Term) arguments[i].substitute(variables);
        }
        return this;
    }

    @Override
    public boolean evaluate() {
        return false;
    }

    @Override
    public StringBuilder asString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        if (arguments.length != 0) {
            sb.append("(");
            for (int i = 0; i < arguments.length; i++) {
                sb.append(arguments[i].asString()).append(i == arguments.length - 1 ? "" : ",");
            }
            sb.append(")");
        }
        return sb;
    }

    @Override
    public StringBuilder asJavaExpr() {
        StringBuilder sb = new StringBuilder("new Predicate(").append("\"" + name + "\"");
        for (int i = 0; i < arguments.length; i++) {
            sb.append(",").append(arguments[i].asJavaExpr());
        }
        sb.append(")");
        return sb;
    }

    @Override
    public List<Expression> getParticularProof(List<? extends Expression> hypos) throws ProofGeneratingException {
        return null;
    }

    @Override
    public HashMap<String, Variable> getVars() {
        return null;
    }

    @Override
    public boolean canSubstitute(Variable var) {
        for (Term t : arguments) {
            if (!t.canSubstitute(var)) return false;
        }
        return true;
    }
}
