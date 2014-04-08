package ru.ifmo.ctddev.isaev.hardcodedRules;

import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.arithmetics.*;
import ru.ifmo.ctddev.isaev.structure.logic.Not;
import ru.ifmo.ctddev.isaev.structure.logic.Then;
import ru.ifmo.ctddev.isaev.structure.logic.Variable;

/**
 * User: Xottab
 * Date: 07.04.14
 */
public enum ArithmeticAxiom {
    A_1(
            new Then(
                    new Equals(
                            new Variable("a"),
                            new Variable("b")
                    ),
                    new Equals(
                            new Prime(new Variable("a")),
                            new Prime(new Variable("b"))
                    )
            )
    ),
    A_2(
            new Then(
                    new Equals(
                            new Variable("a"),
                            new Variable("b")
                    ),
                    new Then(
                            new Equals(
                                    new Variable("a"),
                                    new Variable("c")
                            ),
                            new Equals(
                                    new Variable("b"),
                                    new Variable("c")
                            )
                    )
            )
    ),
    A_3(
            new Then(
                    new Equals(
                            new Prime(new Variable("a")),
                            new Prime(new Variable("b"))),
                    new Equals(
                            new Variable("a"),
                            new Variable("b")
                    )
            )
    ),
    A_4(
            new Not(
                    new Equals(
                            new Prime(new Variable("a")),
                            new Zero()
                    )
            )
    ),
    A_5(
            new Equals(
                    new Plus(
                            new Variable("a"),
                            new Prime(new Variable("b"))
                    ),
                    new Prime(new Plus(
                            new Variable("a"),
                            new Variable("b")
                    ))
            )
    ),
    A_6(
            new Equals(
                    new Plus(
                            new Variable("a"),
                            new Zero()
                    ),
                    new Variable("a")
            )
    ),
    A_7(
            new Equals(
                    new Mul(
                            new Variable("a"),
                            new Zero()
                    ),
                    new Variable("a")
            )
    ),
    A_8(
            new Equals(
                    new Mul(
                            new Variable("a"),
                            new Prime(new Variable("b"))
                    ),
                    new Plus(
                            new Mul(
                                    new Variable("a"),
                                    new Variable("b")
                            ),
                            new Variable("a")
                    )
            )
    );
    Expression expr;

    ArithmeticAxiom(Expression expr) {
        this.expr = expr;
    }
}
