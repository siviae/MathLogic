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
        this.operand = arguments[0];
    }

    @Override
    public StringBuilder asString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Lexeme.LEFT_P).append(operand.asString()).append(Lexeme.RIGHT_P).append(name);
        return sb;
    }

}
