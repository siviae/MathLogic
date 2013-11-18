package ru.ifmo.ctddev.isaev.structure;

import ru.ifmo.ctddev.isaev.parser.Lexeme;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 05.11.13
 * Time: 20:32
 * To change this template use File | Settings | File Templates.
 */
public class LogicalThen extends LogicalBinary {
    public LogicalThen(Expression left, Expression right) {
        super(left, right);
        this.token = Lexeme.THEN;
    }

    @Override
    public boolean match(Expression other) {
        return other instanceof LogicalThen
                && ((LogicalThen) other).left.match(left)
                && ((LogicalThen) other).right.match(right);
    }

    @Override
    public boolean hasSameType(Expression other) {
        return other instanceof LogicalThen;
    }

    @Override
    public boolean evaluate() {
        return !left.evaluate() || right.evaluate();
    }

    @Override
    public Expression substitute(HashMap<String, Expression> variables) {
        return new LogicalThen(left.substitute(variables),right.substitute(variables));
    }
}
