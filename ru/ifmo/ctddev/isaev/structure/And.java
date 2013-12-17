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
public class And extends Binary {
    public And(Expression left, Expression right) {
        super(left, right);
        this.token = Lexeme.AND;
    }

    @Override
    public boolean match(Expression other) {
        return hasSameType(other)
                && ((And) other).left.match(left)
                && ((And) other).right.match(right);
    }

    @Override
    public Expression substitute(HashMap<String, Expression> variables) {
        return new And(left.substitute(variables), right.substitute(variables));
    }

    @Override
    public boolean hasSameType(Expression other) {
        return other instanceof And;
    }

    @Override
    public boolean evaluate() {
        return left.evaluate() && right.evaluate();
    }

    @Override
    public List<Expression> getParticularProof(ArrayList<Expression> hypos) {

        return null;
    }

}
