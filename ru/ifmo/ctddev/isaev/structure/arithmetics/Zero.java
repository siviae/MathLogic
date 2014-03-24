package ru.ifmo.ctddev.isaev.structure.arithmetics;

import ru.ifmo.ctddev.isaev.parser.Lexeme;
import ru.ifmo.ctddev.isaev.structure.predicate.Term;

/**
 * User: Xottab
 * Date: 24.03.14
 */
public class Zero extends Term {

    public Zero() {
        super(Lexeme.ZERO.s);
    }

}
