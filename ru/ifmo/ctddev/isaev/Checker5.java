package ru.ifmo.ctddev.isaev;

import ru.ifmo.ctddev.isaev.exception.IncorrectProofException;
import ru.ifmo.ctddev.isaev.exception.LexingException;
import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
import ru.ifmo.ctddev.isaev.structure.Expression;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static ru.ifmo.ctddev.isaev.General.*;

/**
 * User: Xottab
 * Date: 24.03.14
 */
public class Checker5 extends Homework {
    @Override
    public void doSomething() throws IOException, ParsingException, LexingException, IncorrectProofException, ProofGeneratingException {

        Map<String, Expression> proofed = new HashMap<>();
        Map<Expression, ArrayList<Expression>> mps = new HashMap<>();
        String temp = in.readLine();
        row = 1;
        boolean ok = true;
        while (temp != null) {
            if (row == 29) {
                boolean k = true;
            }
            boolean f = false;
            Expression expr = parse(temp);
           /* for (AxiomScheme scheme : AxiomScheme.values()) {
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
            addToMps(mps, expr); */
            row++;
            out.println(expr);
            temp = in.readLine();

        }
        if (ok) {
            out.println("Доказательство корректно.");
        }

    }
}
