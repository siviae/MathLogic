package ru.ifmo.ctddev.isaev.structure.arithmetics;

import ru.ifmo.ctddev.isaev.parser.Lexeme;
import ru.ifmo.ctddev.isaev.structure.predicate.Term;

/**
 * User: Xottab
 * Date: 24.03.14
 */
public class Plus extends Term {


    private Term left;
    private Term right;

    public Plus(Term left, Term right) {
        super(Lexeme.PLUS.s, left, right);
        this.left = arguments[0];
        this.right = arguments[1];
    }

    @Override
    public StringBuilder asString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Lexeme.LEFT_P).append(left.asString()).append(name).append(right.asString()).append(Lexeme.RIGHT_P);
        return sb;
    }
}
