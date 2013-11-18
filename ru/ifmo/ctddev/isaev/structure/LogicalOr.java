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
public class LogicalOr extends LogicalBinary {
    public LogicalOr(Expression left, Expression right) {
        super(left, right);
        this.token = Lexeme.OR;
    }

    @Override
    public boolean match(Expression other) {
        return hasSameType(other)
                && ((LogicalOr) other).left.match(left)
                && ((LogicalOr) other).right.match(right);
    }
    @Override
    public boolean hasSameType(Expression other) {
        return other instanceof LogicalOr;
    }

    @Override
    public boolean evaluate() {
        return left.evaluate() || right.evaluate();
    }

    @Override
    public Expression substitute(HashMap<String, Expression> variables) {
        return new LogicalOr(left.substitute(variables),right.substitute(variables));
    }

}
