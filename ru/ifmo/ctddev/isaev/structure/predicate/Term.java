package ru.ifmo.ctddev.isaev.structure.predicate;

import javafx.util.Pair;
import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
import ru.ifmo.ctddev.isaev.exception.SubstitutionException;
import ru.ifmo.ctddev.isaev.structure.AbstractExpression;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.logic.Variable;

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
    public boolean hasQuantifier(Variable var) {
        for (Term t : arguments) {
            if (!t.hasQuantifier(var)) return false;
        }
        return true;
    }

    @Override
    public Pair<Boolean, Variable> findSubstitutionAndCheck(Expression other, Variable original, Variable alreadyKnown) throws SubstitutionException {
        if (!hasSameType(other) || this.arguments.length != ((Term) other).arguments.length)
            throw new SubstitutionException();
        if (arguments.length > 0) {
            Variable alreadyKn = alreadyKnown;
            for (int i = 0; i < this.arguments.length; i++) {
                Pair<Boolean, Variable> result = arguments[i].findSubstitutionAndCheck(((Term) other).arguments[i], original, alreadyKn);
                if (result.getKey()) {
                    if (alreadyKn != null) {
                        if (!result.getValue().match(alreadyKn)) {
                            return new Pair<>(false, alreadyKn);
                        }
                    } else alreadyKn = result.getValue();
                } else {
                    if (result.getValue() != null) {
                        return result;
                    }
                }
            }
        }
        return new Pair<>(alreadyKnown != null, alreadyKnown);
    }

    @Override
    public Pair<Boolean, Term> findSubstitutionAndCheck2(Expression other, Variable original, Term alreadyKnown) throws SubstitutionException {
        if (!hasSameType(other) || this.arguments.length != ((Term) other).arguments.length)
            throw new SubstitutionException();
        if (arguments.length > 0) {
            Term alreadyKn = alreadyKnown;
            for (int i = 0; i < this.arguments.length; i++) {
                Pair<Boolean, Term> result = arguments[i].findSubstitutionAndCheck2(((Term) other).arguments[i], original, alreadyKn);
                if (result.getKey()) {
                    if (alreadyKn != null) {
                        if (!result.getValue().match(alreadyKn)) {
                            return new Pair<>(false, alreadyKn);
                        }
                    } else alreadyKn = result.getValue();
                } else {
                    if (result.getValue() != null) {
                        return result;
                    }
                }
            }
            if (alreadyKn != null) {
                return new Pair<>(true, alreadyKn);
            }
        }
        return new Pair<>(alreadyKnown != null, alreadyKnown);
    }
}
