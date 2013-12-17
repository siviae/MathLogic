package ru.ifmo.ctddev.isaev.structure;

import ru.ifmo.ctddev.isaev.parser.Lexeme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 05.11.13
 * Time: 20:33
 * To change this template use File | Settings | File Templates.
 */
public class Not extends Unary {
    public Not(Expression operand) {
        super(operand);
        this.token = Lexeme.NOT;
    }

    @Override
    public boolean match(Expression other) {
        return hasSameType(other)
                && ((Not) other).operand.match(operand);
    }

    @Override
    public boolean hasSameType(Expression other) {
        return other instanceof Not;
    }

    @Override
    public boolean evaluate() {
        return !operand.evaluate();
    }

    @Override
    public List<Expression> getParticularProof(ArrayList<Expression> hypos) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Expression substitute(HashMap<String, Expression> variables) {
        return new Not(operand.substitute(variables));
    }
}
