package ru.ifmo.ctddev.isaev.structure.arithmetics;

import ru.ifmo.ctddev.isaev.parser.Lexeme;
import ru.ifmo.ctddev.isaev.structure.predicate.Term;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * User: Xottab
 * Date: 25.03.14
 */
public class Prime extends Term {

    private Term operand;

    public Prime(Term operand) {
        super(Lexeme.PRIME.s, operand);
        this.operand = arguments[0];
    }

    public Prime(ArrayList<Term> list) {
        super(Lexeme.PRIME.s, list.get(0));
        this.operand = arguments[0];
    }

    @Override
    public Set<String> getFreeVars() {
        HashSet<String> vars = new HashSet<>();
        for (Term t : arguments) {
            vars.addAll(t.getFreeVars());
        }
        return vars;
    }

    public Term getOperand() {
        return operand;
    }

    @Override
    public StringBuilder asString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Lexeme.LEFT_P).append(operand.asString()).append(Lexeme.RIGHT_P).append(name);
        return sb;
    }

}
