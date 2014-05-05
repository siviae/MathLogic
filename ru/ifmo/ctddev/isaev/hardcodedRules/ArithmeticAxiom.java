package ru.ifmo.ctddev.isaev.hardcodedRules;

import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.arithmetics.*;
import ru.ifmo.ctddev.isaev.structure.logic.Not;
import ru.ifmo.ctddev.isaev.structure.logic.Then;
import ru.ifmo.ctddev.isaev.structure.predicate.Term;

/**
 * User: Xottab
 * Date: 07.04.14
 */
public enum ArithmeticAxiom {
    A_1(
            new Then(
                    new Equals(
                            new Term("a"),
                            new Term("b")
                    ),
                    new Equals(
                            new Prime(new Term("a")),
                            new Prime(new Term("b"))
                    )
            )
    ),
    A_2(
            new Then(
                    new Equals(
                            new Term("a"),
                            new Term("b")
                    ),
                    new Then(
                            new Equals(
                                    new Term("a"),
                                    new Term("c")
                            ),
                            new Equals(
                                    new Term("b"),
                                    new Term("c")
                            )
                    )
            )
    ),
    A_3(
            new Then(
                    new Equals(
                            new Prime(new Term("a")),
                            new Prime(new Term("b"))),
                    new Equals(
                            new Term("a"),
                            new Term("b")
                    )
            )
    ),
    A_4(
            new Not(
                    new Equals(
                            new Prime(new Term("a")),
                            new Zero()
                    )
            )
    ),
    A_5(
            new Equals(
                    new Plus(
                            new Term("a"),
                            new Prime(new Term("b"))
                    ),
                    new Prime(new Plus(
                            new Term("a"),
                            new Term("b")
                    ))
            )
    ),
    A_6(
            new Equals(
                    new Plus(
                            new Term("a"),
                            new Zero()
                    ),
                    new Term("a")
            )
    ),
    A_7(
            new Equals(
                    new Mul(
                            new Term("a"),
                            new Zero()
                    ),
                    new Term("a")
            )
    ),
    A_8(
            new Equals(
                    new Mul(
                            new Term("a"),
                            new Prime(new Term("b"))
                    ),
                    new Plus(
                            new Mul(
                                    new Term("a"),
                                    new Term("b")
                            ),
                            new Term("a")
                    )
            )
    );
    Expression expr;

    ArithmeticAxiom(Expression expr) {
        this.expr = expr;
    }

    public boolean match(Expression expr) {
        return this.expr.match(expr);
    }
}
