package ru.ifmo.ctddev.isaev.structure;

import ru.ifmo.ctddev.isaev.parser.Lexeme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 05.11.13
 * Time: 20:32
 * To change this template use File | Settings | File Templates.
 */
public class Or extends Binary {
    public Or(Expression left, Expression right) {
        super(left, right);
        this.token = Lexeme.OR;
    }

    @Override
    public boolean match(Expression other) {
        return hasSameType(other)
                && ((Or) other).left.match(left)
                && ((Or) other).right.match(right);
    }

    @Override
    public boolean hasSameType(Expression other) {
        return other instanceof Or;
    }

    @Override
    public boolean evaluate() {
        return left.evaluate() || right.evaluate();
    }

    @Override
    public List<Expression> getParticularProof(ArrayList<Expression> hypos) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Expression substitute(HashMap<String, Expression> variables) {
        return new Or(left.substitute(variables), right.substitute(variables));
    }

}
