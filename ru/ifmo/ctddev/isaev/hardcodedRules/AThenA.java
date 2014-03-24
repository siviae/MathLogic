package ru.ifmo.ctddev.isaev.hardcodedRules;

import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.logic.NumExpr;
import ru.ifmo.ctddev.isaev.structure.logic.Then;

import java.util.HashMap;
import java.util.Map;

import static ru.ifmo.ctddev.isaev.General.parse;

/**
 * User: Xottab
 * Date: 24.01.14
 */
public enum AThenA {
    R_1(new Then(new NumExpr(1), new Then(new NumExpr(1), new NumExpr(1)))),
    R_2(new Then(new NumExpr(1), new Then(new Then(new NumExpr(1), new NumExpr(1)), new NumExpr(1)))),
    R_3(new Then(new Then(new NumExpr(1), new Then(new NumExpr(1), new NumExpr(1))), new Then(new Then(new NumExpr(1), new Then(new Then(new NumExpr(1), new NumExpr(1)), new NumExpr(1))), new Then(new NumExpr(1), new NumExpr(1))))),
    R_4(new Then(new Then(new NumExpr(1), new Then(new Then(new NumExpr(1), new NumExpr(1)), new NumExpr(1))), new Then(new NumExpr(1), new NumExpr(1)))),
    R_5(new Then(new NumExpr(1), new NumExpr(1)));

    private Expression expression;

    AThenA(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public Expression replace(Expression e) {
        Map<String, Expression> map = new HashMap<>();
        map.put("1", e);
        return expression.substituteAndCopy(map);
    }

    public String replace(String e) {
        return replace(parse(e)).toString();
    }
}
