package ru.ifmo.ctddev.isaev.helpers;

import ru.ifmo.ctddev.isaev.structure.*;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 06.11.13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
public enum AxiomScheme {
    SC_1(new Then(
            new NumExpression(1),
            new Then(
                    new NumExpression(2),
                    new NumExpression(1)))),
    SC_2(new Then(
            new Then(
                    new NumExpression(1),
                    new NumExpression(2)
            ),
            new Then(
                    new Then(
                            new NumExpression(1),
                            new Then(
                                    new NumExpression(2),
                                    new NumExpression(3)
                            )
                    ),
                    new Then(
                            new NumExpression(1),
                            new NumExpression(3)
                    )
            )
    )),
    SC_3(new Then(
            new NumExpression(1),
            new Then(
                    new NumExpression(2),
                    new And(
                            new NumExpression(1),
                            new NumExpression(2)
                    )
            )
    )),
    SC_4(new Then(
            new And(
                    new NumExpression(1),
                    new NumExpression(2)
            ),
            new NumExpression(1)
    )),
    SC_5(new Then(
            new And(
                    new NumExpression(1),
                    new NumExpression(2)
            ),
            new NumExpression(2)
    )),
    SC_6(new Then(
            new NumExpression(1),
            new Or(
                    new NumExpression(1),
                    new NumExpression(2)
            )
    )),
    SC_7(new Then(
            new NumExpression(2),
            new Or(
                    new NumExpression(1),
                    new NumExpression(2)
            )
    )),
    SC_8(new Then(
            new Then(
                    new NumExpression(1),
                    new NumExpression(3)
            ),
            new Then(
                    new Then(
                            new NumExpression(2),
                            new NumExpression(3)
                    ),
                    new Then(
                            new Or(
                                    new NumExpression(1),
                                    new NumExpression(2)
                            ),
                            new NumExpression(3)
                    )
            )
    )),
    SC_9(new Then(
            new Then(
                    new NumExpression(1),
                    new NumExpression(2)
            ),
            new Then(
                    new Then(
                            new NumExpression(1),
                            new Not(new NumExpression(2))
                    ),
                    new Not(new NumExpression(1)))
    )),
    SC_10(new Then(
            new Not(new Not(new NumExpression(1))),
            new NumExpression(1)));

    private Expression expr;


    private AxiomScheme(Expression expr) {
        this.expr = expr;
    }

    public boolean match(Expression expr) {
        return this.expr.matchAxiomScheme(expr, new HashMap<Integer, Expression>());
    }

    public Expression substitute(HashMap<String, Expression> map) {
        return this.expr.substitute(map);
    }
}
