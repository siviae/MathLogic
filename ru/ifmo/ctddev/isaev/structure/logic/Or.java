package ru.ifmo.ctddev.isaev.structure.logic;

import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
import ru.ifmo.ctddev.isaev.parser.Lexeme;
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
public class Or extends Binary {
    public Or(Expression left, Expression right) {
        super(left, right);
        this.token = Lexeme.OR;
    }

    @Override
    public boolean evaluate() {
        return left.evaluate() || right.evaluate();
    }

    @Override
    public List<Expression> getParticularProof(List<? extends Expression> hypos) throws ProofGeneratingException {
        List<Expression> result = super.getParticularProof(hypos);
        boolean l = left.evaluate();
        boolean r = right.evaluate();
        Expression a = left;
        Expression b = right;
        if (l & r) {
            result.add(new Then(a, new Or(a, b)));
            result.add(a);
            result.add(new Or(a, b));
        } else if (!l & r) {
            result.add(new Then(b, new Or(a, b)));
            result.add(b);
            result.add(new Or(a, b));
        } else if (l & !r) {
            result.add(new Then(a, new Or(a, b)));
            result.add(a);
            result.add(new Or(a, b));
        } else if (!l & !r) {
            result.add(new Then(a, new Then(a, a)));
            result.add(new Then(new Then(a, new Then(a, a)), new Then(new Then(a, new Then(new Then(a, a), a)), new Then(a, a))));
            result.add(new Then(new Then(a, new Then(new Then(a, a), a)), new Then(a, a)));
            result.add(new Then(a, new Then(new Then(a, a), a)));
            result.add(new Then(a, a));
            result.add(new Then(new Then(new And(new Not(a), new Not(b)), a), new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b))))));
            result.add(new Then(new Then(new Then(new And(new Not(a), new Not(b)), a), new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b))))), new Then(a, new Then(new Then(new And(new Not(a), new Not(b)), a), new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b))))))));
            result.add(new Then(a, new Then(new Then(new And(new Not(a), new Not(b)), a), new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b)))))));
            result.add(new Then(a, new Then(new And(new Not(a), new Not(b)), a)));
            result.add(new Then(new Then(a, new Then(new And(new Not(a), new Not(b)), a)), new Then(a, new Then(a, new Then(new And(new Not(a), new Not(b)), a)))));
            result.add(new Then(a, new Then(a, new Then(new And(new Not(a), new Not(b)), a))));
            result.add(new Then(new Then(a, a), new Then(new Then(a, new Then(a, new Then(new And(new Not(a), new Not(b)), a))), new Then(a, new Then(new And(new Not(a), new Not(b)), a)))));
            result.add(new Then(new Then(a, new Then(a, new Then(new And(new Not(a), new Not(b)), a))), new Then(a, new Then(new And(new Not(a), new Not(b)), a))));
            result.add(new Then(a, new Then(new And(new Not(a), new Not(b)), a)));
            result.add(new Then(new Then(a, new Then(new And(new Not(a), new Not(b)), a)), new Then(new Then(a, new Then(new Then(new And(new Not(a), new Not(b)), a), new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b)))))), new Then(a, new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b))))))));
            result.add(new Then(new Then(a, new Then(new Then(new And(new Not(a), new Not(b)), a), new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b)))))), new Then(a, new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b)))))));
            result.add(new Then(a, new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b))))));
            result.add(new Then(new And(new Not(a), new Not(b)), new Not(a)));
            result.add(new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Then(a, new Then(new And(new Not(a), new Not(b)), new Not(a)))));
            result.add(new Then(a, new Then(new And(new Not(a), new Not(b)), new Not(a))));
            result.add(new Then(new Then(a, new Then(new And(new Not(a), new Not(b)), new Not(a))), new Then(new Then(a, new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b))))), new Then(a, new Not(new And(new Not(a), new Not(b)))))));
            result.add(new Then(new Then(a, new Then(new Then(new And(new Not(a), new Not(b)), new Not(a)), new Not(new And(new Not(a), new Not(b))))), new Then(a, new Not(new And(new Not(a), new Not(b))))));
            result.add(new Then(a, new Not(new And(new Not(a), new Not(b)))));
            result.add(new Then(b, new Then(b, b)));
            result.add(new Then(new Then(b, new Then(b, b)), new Then(new Then(b, new Then(new Then(b, b), b)), new Then(b, b))));
            result.add(new Then(new Then(b, new Then(new Then(b, b), b)), new Then(b, b)));
            result.add(new Then(b, new Then(new Then(b, b), b)));
            result.add(new Then(b, b));
            result.add(new Then(new Then(new And(new Not(a), new Not(b)), b), new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b))))));
            result.add(new Then(new Then(new Then(new And(new Not(a), new Not(b)), b), new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b))))), new Then(b, new Then(new Then(new And(new Not(a), new Not(b)), b), new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b))))))));
            result.add(new Then(b, new Then(new Then(new And(new Not(a), new Not(b)), b), new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b)))))));
            result.add(new Then(b, new Then(new And(new Not(a), new Not(b)), b)));
            result.add(new Then(new Then(b, new Then(new And(new Not(a), new Not(b)), b)), new Then(b, new Then(b, new Then(new And(new Not(a), new Not(b)), b)))));
            result.add(new Then(b, new Then(b, new Then(new And(new Not(a), new Not(b)), b))));
            result.add(new Then(new Then(b, b), new Then(new Then(b, new Then(b, new Then(new And(new Not(a), new Not(b)), b))), new Then(b, new Then(new And(new Not(a), new Not(b)), b)))));
            result.add(new Then(new Then(b, new Then(b, new Then(new And(new Not(a), new Not(b)), b))), new Then(b, new Then(new And(new Not(a), new Not(b)), b))));
            result.add(new Then(b, new Then(new And(new Not(a), new Not(b)), b)));
            result.add(new Then(new Then(b, new Then(new And(new Not(a), new Not(b)), b)), new Then(new Then(b, new Then(new Then(new And(new Not(a), new Not(b)), b), new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b)))))), new Then(b, new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b))))))));
            result.add(new Then(new Then(b, new Then(new Then(new And(new Not(a), new Not(b)), b), new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b)))))), new Then(b, new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b)))))));
            result.add(new Then(b, new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b))))));
            result.add(new Then(new And(new Not(a), new Not(b)), new Not(b)));
            result.add(new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Then(b, new Then(new And(new Not(a), new Not(b)), new Not(b)))));
            result.add(new Then(b, new Then(new And(new Not(a), new Not(b)), new Not(b))));
            result.add(new Then(new Then(b, new Then(new And(new Not(a), new Not(b)), new Not(b))), new Then(new Then(b, new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b))))), new Then(b, new Not(new And(new Not(a), new Not(b)))))));
            result.add(new Then(new Then(b, new Then(new Then(new And(new Not(a), new Not(b)), new Not(b)), new Not(new And(new Not(a), new Not(b))))), new Then(b, new Not(new And(new Not(a), new Not(b))))));
            result.add(new Then(b, new Not(new And(new Not(a), new Not(b)))));
            result.add(new Then(new Then(a, new Not(new And(new Not(a), new Not(b)))), new Then(new Then(b, new Not(new And(new Not(a), new Not(b)))), new Then(new Or(a, b), new Not(new And(new Not(a), new Not(b)))))));
            result.add(new Then(new Then(b, new Not(new And(new Not(a), new Not(b)))), new Then(new Or(a, b), new Not(new And(new Not(a), new Not(b))))));
            result.add(new Then(new Or(a, b), new Not(new And(new Not(a), new Not(b)))));
            result.add(new Then(new Not(a), new Then(new Not(b), new And(new Not(a), new Not(b)))));
            result.add(new Then(new Not(b), new And(new Not(a), new Not(b))));
            result.add(new And(new Not(a), new Not(b)));
            result.add(new Then(new And(new Not(a), new Not(b)), new Then(new Or(a, b), new And(new Not(a), new Not(b)))));
            result.add(new Then(new Or(a, b), new And(new Not(a), new Not(b))));
            result.add(new Then(new Then(new Or(a, b), new And(new Not(a), new Not(b))), new Then(new Then(new Or(a, b), new Not(new And(new Not(a), new Not(b)))), new Not(new Or(a, b)))));
            result.add(new Then(new Then(new Or(a, b), new Not(new And(new Not(a), new Not(b)))), new Not(new Or(a, b))));
            result.add(new Not(new Or(a, b)));
        } else throw new ProofGeneratingException("no expressions were added, incorrect behavior");
        return result;
    }

    @Override
    public Expression substituteAndCopy(Map<String, ? extends Expression> variables) {
        return new Or(left.substituteAndCopy(variables), right.substituteAndCopy(variables));
    }

}
