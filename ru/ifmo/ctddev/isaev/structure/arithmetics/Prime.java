package ru.ifmo.ctddev.isaev.structure.arithmetics;

import ru.ifmo.ctddev.isaev.parser.Lexeme;
import ru.ifmo.ctddev.isaev.structure.predicate.Term;

/**
 * User: Xottab
 * Date: 25.03.14
 */
public class Prime extends Term {

    private Term operand;

    public Prime(Term operand) {
        super(Lexeme.PRIME.s, operand);
        operand = arguments[0];
    }

}
