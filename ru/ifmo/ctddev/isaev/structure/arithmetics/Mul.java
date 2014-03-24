package ru.ifmo.ctddev.isaev.structure.arithmetics;

import ru.ifmo.ctddev.isaev.parser.Lexeme;
import ru.ifmo.ctddev.isaev.structure.predicate.Term;

/**
 * User: Xottab
 * Date: 24.03.14
 */
public class Mul extends Term {
    private Term left;
    private Term right;

    public Mul(Term left, Term right) {
        super(Lexeme.MUL.s, left, right);
        left = arguments[0];
        right = arguments[1];
    }
}
