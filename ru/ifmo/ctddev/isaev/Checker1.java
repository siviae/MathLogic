package ru.ifmo.ctddev.isaev;

import ru.ifmo.ctddev.isaev.hardcodedRules.AxiomScheme;
import ru.ifmo.ctddev.isaev.structure.Expression;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.ifmo.ctddev.isaev.General.*;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 10.11.13
 * Time: 13:21
 * To change this template use File | Settings | File Templates.
 */
public class Checker1 extends Homework {


    @Override
    public void doSomething() throws IOException {
        Map<String, Expression> proofed = new HashMap<>();
        Map<Expression, List<Expression>> mps = new HashMap<>();
        String temp = in.readLine();
        row = 1;
        boolean ok = true;
        while (temp != null) {
            boolean f = false;
            Expression expr = parse(temp);
            for (AxiomScheme scheme : AxiomScheme.values()) {
                f = scheme.match(expr);
                if (f) {
                    break;
                }
            }
            if (proofed.containsKey(expr.toString())) {
                f = true;
            }
            if (!f) {
                if (mps.get(expr) != null) {
                    for (Expression e : mps.get(expr)) {
                        if (proofed.get(e.toString()) != null) {
                            f = true;
                            break;
                        }
                    }
                }
            }
            if (!f) {
                out.println("Доказательство некорректно начиная с " + row + " высказывания.");
                ok = false;
                break;
            }
            proofed.put(expr.toString(), expr);
            addToMps(mps, expr);
            row++;
            temp = in.readLine();

        }
        if (ok) {
            out.println("Доказательство корректно.");
        }

    }
}
