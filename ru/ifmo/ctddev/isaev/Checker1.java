package ru.ifmo.ctddev.isaev;

import ru.ifmo.ctddev.isaev.helpers.AxiomScheme;
import ru.ifmo.ctddev.isaev.structure.Expression;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        List<Expression> proofed = new ArrayList<>();
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
            if (!f) {
                for (int i = proofed.size() - 1; i >= 0; i--) {
                    f = proofed.get(i).match(expr);
                    for (int j = proofed.size() - 1; j >= 0; j--) {
                        Expression aProofed = proofed.get(j);
                        f = f || modusPonens(proofed.get(i), aProofed, expr);
                    }
                    if (f) break;
                }
            }
            if (!f) {
                out.println("Доказательство некорректно начиная с " + row + " высказывания.");
                ok = false;
                break;
            }
            proofed.add(expr);
            row++;
            temp = in.readLine();

        }
        if (ok) {
            out.println("Доказательство корректно.");
        }

    }
}
