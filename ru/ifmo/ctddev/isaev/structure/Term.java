package ru.ifmo.ctddev.isaev.structure;

import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Xottab
 * Date: 17.12.13
 */
public class Term extends AbstractExpression {
    protected String name;
    protected Term[] arguments;

    public Term(String token) {
        this.arguments = new Term[0];
        this.name = token;
    }

    public Term(String token, Term... terms) {
        this.arguments = terms;
        this.name = token;
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
    public boolean treeEquals(Expression other) {
        if (hasSameType(other)) {
            Term pred = (Term) other;
            if (name.equals(pred.name) && arguments.length == pred.arguments.length) {
                boolean f = false;
                for (int i = 0; i < arguments.length; i++) {
                    if (!arguments[i].treeEquals(pred.arguments[i])) {
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
    public boolean match(Expression other) {
        return hasSameType(other)
                && ((Term) other).getName().equals(name)
                && ((Term) other).arguments.length == arguments.length;
    }

    @Override
    public boolean matchAxiomScheme(Expression expr, Map<Integer, Expression> known) {
        return false;
    }

    @Override
    public Expression substituteAndCopy(Map<String, ? extends Expression> variables) {
        Term term = new Term(this.name);
        Term[] args = new Term[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            args[i] = (Term) arguments[i].substitute(variables);
        }
        term.setArguments(args);
        return term;
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
        StringBuilder sb = new StringBuilder("new Term(").append("\"" + name + "\"");
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
    public Map<String, Variable> getVars() {
        Map<String, Variable> h = null;
        if (arguments.length == 0) {
            h = new HashMap<>();
            h.put(name, new Variable(name));
        } else {
            for (int i = 0; i < arguments.length; i++) {
                if (i == 0) {
                    h = arguments[i].getVars();
                } else {
                    h.putAll(arguments[i].getVars());
                }
            }
        }
        return h;
    }

    @Override
    public boolean canSubstitute(Variable var) {
        for (Term t : arguments) {
            if (!t.canSubstitute(var)) return false;
        }
        return true;
    }
}
