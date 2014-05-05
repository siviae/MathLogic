package ru.ifmo.ctddev.isaev.structure.predicate;

import javafx.util.Pair;
import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
import ru.ifmo.ctddev.isaev.exception.TreeMismatchException;
import ru.ifmo.ctddev.isaev.structure.AbstractExpression;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.logic.Variable;

import java.util.*;

/**
 * User: Xottab
 * Date: 17.12.13
 */
public class Term extends AbstractExpression {
    public Set<String> quantifiers;
    public boolean isFree = false;
    protected String name;
    protected Term[] arguments;

    public Term(String token) {
        this.arguments = new Term[0];
        quantifiers = new HashSet<>(3);
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
        /*if (hasSameType(other)) {
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
        return false;*/
        return hasSameType(other);
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
    public boolean hasSameType(Expression other) {
        return false;
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

    public boolean matchAnotherTerm(Term t) {
        return (t.name.equals(name) && t.arguments.length == arguments.length);
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
    public Set<String> getFreeVars() {
        HashSet<String> vars = new HashSet<>();
        for (Term t : arguments) {
            vars.addAll(t.getFreeVars());
        }
        if (!this.quantifiers.contains(this.name))
            vars.add(name);
        return vars;
    }

    @Override
    public boolean hasQuantifier(Variable var) {
        for (Term t : arguments) {
            if (!t.hasQuantifier(var)) return false;
        }
        return true;
    }

    @Override
    public void setQuantifiers(Map<String, Quantifier> quantifiers) {
        Set<String> set = new HashSet<>();
        set.addAll(quantifiers.keySet());
        this.quantifiers = set;
    }

    @Override
    public int markFreeVariableOccurences(String variableName) {
        int result = 0;
        for (Term t : arguments) {
            result += t.markFreeVariableOccurences(variableName);
        }
        if (this.name.equals(variableName)) {
            boolean f = true;
            for (String s : quantifiers) {
                if (s.equals(variableName)) {
                    f = false;
                    break;
                }
            }
            if (f) {
                isFree = true;
                result++;
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }

    public List<String> getTermNames() {
        List<String> result;
        if (arguments.length == 0) {
            result = new ArrayList<>(10);
        } else {
            result = arguments[0].getTermNames();
            for (int i = 1; i < arguments.length; i++) {
                result.addAll(arguments[i].getTermNames());
            }
        }
        return result;
    }

    @Override
    public Set<Pair<Term, Term>> getReplacedVariableOccurences(Expression originalExpr) throws TreeMismatchException {
        Set<Pair<Term, Term>> set = new HashSet<>();
        if (!(originalExpr instanceof Term) || !matchAnotherTerm((Term) originalExpr))
            throw new TreeMismatchException(originalExpr, this);
        if (((Term) originalExpr).isFree) {
            set.add(new Pair<>((Term) originalExpr, this));
        }
        for (int i = 0; i < arguments.length; i++) {
            Term t = arguments[i];
            set.addAll(t.getReplacedVariableOccurences(((Term) originalExpr).arguments[i]));
        }
        return set;
    }
}
