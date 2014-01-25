package ru.ifmo.ctddev.isaev.structure.logic;

import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
import ru.ifmo.ctddev.isaev.parser.Lexeme;
import ru.ifmo.ctddev.isaev.structure.Binary;
import ru.ifmo.ctddev.isaev.structure.Expression;

import java.util.List;
import java.util.Map;

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
    public Expression substituteAndCopy(Map<String, ? extends Expression> variables) {
        return new And(left.substituteAndCopy(variables), right.substituteAndCopy(variables));
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
    public List<Expression> getParticularProof(List<? extends Expression> hypos) throws ProofGeneratingException {
        List<Expression> result = super.getParticularProof(hypos);

        boolean l = left.evaluate();
        boolean r = right.evaluate();
        Expression a = left;
        Expression b = right;
        if (l & r) {
            result.add(new Then(a, new Then(b, new And(a, b))));
            result.add(a);
            result.add(b);
            result.add(new Then(b, new And(a, b)));
            result.add(new And(a, b));
        } else if (!l&r) {
            result.add(new Then(new Then(new And(a, b), a), new Then(new Then(new And(a, b), new Not(a)), new Not(new And(a, b)))));
            result.add(new Then(new And(a, b), a));
            result.add(new Then(new Then(new And(a, b), new Not(a)), new Not(new And(a, b))));
            result.add(new Then(new Not(a), new Then(new And(a, b), new Not(a))));
            result.add(new Not(a));
            result.add(new Then(new And(a, b), new Not(a)));
            result.add(new Not(new And(a, b)));
        } else if (l&!r) {
            result.add(new Then(new Then(new And(a, b), b), new Then(new Then(new And(a, b), new Not(b)), new Not(new And(a, b)))));
            result.add(new Then(new And(a, b), b));
            result.add(new Then(new Then(new And(a, b), new Not(b)), new Not(new And(a, b))));
            result.add(new Then(new Not(b), new Then(new And(a, b), new Not(b))));
            result.add(new Not(b));
            result.add(new Then(new And(a, b), new Not(b)));
            result.add(new Not(new And(a, b)));
        } else if (!l&!r) {
            result.add(new Then(new Then(new And(a, b), a), new Then(new Then(new And(a, b), new Not(a)), new Not(new And(a, b)))));
            result.add(new Then(new And(a, b), a));
            result.add(new Then(new Then(new And(a, b), new Not(a)), new Not(new And(a, b))));
            result.add(new Then(new Not(a), new Then(new And(a, b), new Not(a))));
            result.add(new Not(a));
            result.add(new Then(new And(a, b), new Not(a)));
            result.add(new Not(new And(a, b)));
        } else throw new ProofGeneratingException("no expressions were added, incorrect behavior");
        return result;
    }

}
