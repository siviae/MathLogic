package ru.ifmo.ctddev.isaev.structure.logic;

import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
import ru.ifmo.ctddev.isaev.parser.Lexeme;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.NumExpression;
import ru.ifmo.ctddev.isaev.structure.Unary;

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
    public List<Expression> getParticularProof(List<? extends Expression> hypos) throws ProofGeneratingException {
        List<Expression> result = super.getParticularProof(hypos);
        Expression a = operand;
        if (this.match(new Not(new Not(new NumExpression(1))))) {
            result.add(new Then(new Then(new Not(a), a), new Then(new Then(new Not(a), new Not(a)), new Not(new Not(a)))));
            result.add(new Then(a, new Then(new Not(a), a)));
            result.add(a);
            result.add(new Then(new Not(a), a));
            result.add(new Then(new Then(new Not(a), new Not(a)), new Not(new Not(a))));
            result.add(new Then(new Not(a), new Then(new Not(a), new Not(a))));
            result.add(new Then(new Then(new Not(a), new Then(new Not(a), new Not(a))), new Then(new Then(new Not(a), new Then(new Then(new Not(a), new Not(a)), new Not(a))), new Then(new Not(a), new Not(a)))));
            result.add(new Then(new Then(new Not(a), new Then(new Then(new Not(a), new Not(a)), new Not(a))), new Then(new Not(a), new Not(a))));
            result.add(new Then(new Not(a), new Then(new Then(new Not(a), new Not(a)), new Not(a))));
            result.add(new Then(new Not(a), new Not(a)));
            result.add(new Not(new Not(a)));
        } else if (this.match(new Not(new NumExpression(1)))) {
            result.add(new Not(a));
        }
        return result;
    }

    @Override
    public Expression substitute(HashMap<String, ? extends Expression> variables) {
        return new Not(operand.substitute(variables));
    }
}
