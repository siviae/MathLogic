package ru.ifmo.ctddev.isaev.helpers;

import ru.ifmo.ctddev.isaev.structure.*;
import ru.ifmo.ctddev.isaev.structure.logic.And;
import ru.ifmo.ctddev.isaev.structure.logic.Not;
import ru.ifmo.ctddev.isaev.structure.logic.Or;
import ru.ifmo.ctddev.isaev.structure.logic.Then;

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
            new NumExpr(1),
            new Then(
                    new NumExpr(2),
                    new NumExpr(1)))),
    SC_2(new Then(
            new Then(
                    new NumExpr(1),
                    new NumExpr(2)
            ),
            new Then(
                    new Then(
                            new NumExpr(1),
                            new Then(
                                    new NumExpr(2),
                                    new NumExpr(3)
                            )
                    ),
                    new Then(
                            new NumExpr(1),
                            new NumExpr(3)
                    )
            )
    )),
    SC_3(new Then(
            new NumExpr(1),
            new Then(
                    new NumExpr(2),
                    new And(
                            new NumExpr(1),
                            new NumExpr(2)
                    )
            )
    )),
    SC_4(new Then(
            new And(
                    new NumExpr(1),
                    new NumExpr(2)
            ),
            new NumExpr(1)
    )),
    SC_5(new Then(
            new And(
                    new NumExpr(1),
                    new NumExpr(2)
            ),
            new NumExpr(2)
    )),
    SC_6(new Then(
            new NumExpr(1),
            new Or(
                    new NumExpr(1),
                    new NumExpr(2)
            )
    )),
    SC_7(new Then(
            new NumExpr(2),
            new Or(
                    new NumExpr(1),
                    new NumExpr(2)
            )
    )),
    SC_8(new Then(
            new Then(
                    new NumExpr(1),
                    new NumExpr(3)
            ),
            new Then(
                    new Then(
                            new NumExpr(2),
                            new NumExpr(3)
                    ),
                    new Then(
                            new Or(
                                    new NumExpr(1),
                                    new NumExpr(2)
                            ),
                            new NumExpr(3)
                    )
            )
    )),
    SC_9(new Then(
            new Then(
                    new NumExpr(1),
                    new NumExpr(2)
            ),
            new Then(
                    new Then(
                            new NumExpr(1),
                            new Not(new NumExpr(2))
                    ),
                    new Not(new NumExpr(1)))
    )),
    SC_10(new Then(
            new Not(new Not(new NumExpr(1))),
            new NumExpr(1)));

    private Expression expr;

    public Expression getExpr() {
        return expr;
    }

    private AxiomScheme(Expression expr) {
        this.expr = expr;
    }

    public boolean match(Expression expr) {
        return this.expr.matchAxiomScheme(expr, new HashMap<Integer, Expression>());
    }

    public Expression substitute(HashMap<String, Expression> map) {
        return this.expr.substituteAndCopy(map);
    }
}
