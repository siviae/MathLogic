package ru.ifmo.ctddev.isaev.structure.arithmetics;

import ru.ifmo.ctddev.isaev.parser.Lexeme;
import ru.ifmo.ctddev.isaev.structure.predicate.Term;

import java.util.HashSet;
import java.util.Set;

/**
 * User: Xottab
 * Date: 24.03.14
 */
public class Zero extends Term {

    public Zero() {
        super(Lexeme.ZERO.s);
    }

    @Override
    public Set<String> getFreeVars() {
        HashSet<String> vars = new HashSet<>();
        return vars;
    }

}
