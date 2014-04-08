package ru.ifmo.ctddev.isaev.structure.arithmetics;

import ru.ifmo.ctddev.isaev.parser.Lexeme;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.predicate.Predicate;
import ru.ifmo.ctddev.isaev.structure.predicate.Term;

/**
 * User: Xottab
 * Date: 24.03.14
 */
public class Equals extends Predicate {
    private Expression left;
    private Expression right;

    public Equals(Term term1, Term term2) {
        super(Lexeme.EQ.s, term1, term2);
        left = arguments[0];
        right = arguments[1];
    }
}
