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

    public String getName() {
        return name;
    }

    public void setArguments(Term[] arguments) {
        this.arguments = arguments;
    }

    public Term[] getArguments() {
        return arguments;
    }

    public Predicate(String name) {
        super();
        this.name = name;
    }

    @Override
    public boolean match(Expression other) {
        return false;
    }

    @Override
    public boolean matchAxiomScheme(Expression expr, HashMap<Integer, Expression> known) {
        return false;
    }

    @Override
    public Expression substituteAndCopy(Map<String, ? extends Expression> variables) {
        return null;
    }

    @Override
    public Expression substitute(Map<String, ? extends Expression> variables) {
        return null;
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
        return  null;
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
