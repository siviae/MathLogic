package ru.ifmo.ctddev.isaev.structure;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 06.11.13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
public enum AxiomScheme {
    SC_1(new LogicalThen(
            new NumExpression(1),
            new LogicalThen(
                    new NumExpression(2),
                    new NumExpression(1)))),
    SC_2(new LogicalThen(
            new LogicalThen(
                    new NumExpression(1),
                    new NumExpression(2)
            ),
            new LogicalThen(
                    new LogicalThen(
                            new NumExpression(1),
                            new LogicalThen(
                                    new NumExpression(2),
                                    new NumExpression(3)
                            )
                    ),
                    new LogicalThen(
                            new NumExpression(1),
                            new NumExpression(3)
                    )
            )
    )),
    SC_3(new LogicalThen(
            new NumExpression(1),
            new LogicalThen(
                    new NumExpression(2),
                    new LogicalAnd(
                            new NumExpression(1),
                            new NumExpression(2)
                    )
            )
    )),
    SC_4(new LogicalThen(
            new LogicalAnd(
                    new NumExpression(1),
                    new NumExpression(2)
            ),
            new NumExpression(1)
    )),
    SC_5(new LogicalThen(
            new LogicalAnd(
                    new NumExpression(1),
                    new NumExpression(2)
            ),
            new NumExpression(2)
    )),
    SC_6(new LogicalThen(
            new NumExpression(1),
            new LogicalOr(
                    new NumExpression(1),
                    new NumExpression(2)
            )
    )),
    SC_7(new LogicalThen(
            new NumExpression(2),
            new LogicalOr(
                    new NumExpression(1),
                    new NumExpression(2)
            )
    )),
    SC_8(new LogicalThen(
            new LogicalThen(
                    new NumExpression(1),
                    new NumExpression(3)
            ),
            new LogicalThen(
                    new LogicalThen(
                            new NumExpression(2),
                            new NumExpression(3)
                    ),
                    new LogicalThen(
                            new LogicalOr(
                                    new NumExpression(1),
                                    new NumExpression(2)
                            ),
                            new NumExpression(3)
                    )
            )
    )),
    SC_9(new LogicalThen(
            new LogicalThen(
                    new NumExpression(1),
                    new NumExpression(2)
            ),
            new LogicalThen(
                    new LogicalThen(
                            new NumExpression(1),
                            new LogicalNot(new NumExpression(2))
                    ),
                    new LogicalNot(new NumExpression(1)))
    )),
    SC_10(new LogicalThen(
            new LogicalNot(new LogicalNot(new NumExpression(1))),
            new NumExpression(1)));

    private Expression expr;


    private AxiomScheme(Expression expr) {
        this.expr = expr;
    }

    public  boolean match(Expression expr) {
        return this.expr.matchAxiomScheme(expr, new HashMap<Integer, Expression>());
    }
}
