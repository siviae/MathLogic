package ru.ifmo.ctddev.isaev;

import javafx.util.Pair;
import ru.ifmo.ctddev.isaev.exception.*;
import ru.ifmo.ctddev.isaev.hardcodedRules.ArithmeticAxiom;
import ru.ifmo.ctddev.isaev.hardcodedRules.AxiomScheme;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.arithmetics.Prime;
import ru.ifmo.ctddev.isaev.structure.arithmetics.Zero;
import ru.ifmo.ctddev.isaev.structure.logic.And;
import ru.ifmo.ctddev.isaev.structure.logic.Then;
import ru.ifmo.ctddev.isaev.structure.logic.Variable;
import ru.ifmo.ctddev.isaev.structure.predicate.Exists;
import ru.ifmo.ctddev.isaev.structure.predicate.ForAll;
import ru.ifmo.ctddev.isaev.structure.predicate.Term;

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
            boolean f = false;
            Expression expr = parse(temp);
            if (proofed.containsKey(expr.toString())) {
                f = true;
            }
            if (row == 5) {
                boolean c = true;
            }
            if (!f) {
                if (expr instanceof Then
                        && ((Then) expr).getLeft() instanceof ForAll
                        /*&& ((ForAll) ((Then) expr).getLeft()).getOperand().match(((Then) expr).getRight())*/) {
                    Variable var = ((ForAll) ((Then) expr).getLeft()).var;
                    try {
                        Pair<Boolean, Term> pair = ((ForAll) ((Then) expr).getLeft()).getOperand().findSubstitutionAndCheck2(((Then) expr).getRight(), var, null);
                        if (pair.getKey()) {
                            f = true;
                        }
                    } catch (SubstitutionException e) {
                        //break;
                        //exitWithMessage("Случилость что-то печальное на строке " + row);
                    }
                }
            }
            if (!f) {
                if (expr instanceof Then
                        && ((Then) expr).getRight() instanceof Exists
                        /*&& ((Exists) ((Then) expr).getRight()).getOperand().match(((Then) expr).getLeft())*/) {
                    Variable var = ((Exists) ((Then) expr).getRight()).var;
                    try {
                        Pair<Boolean, Term> pair = ((Exists) ((Then) expr).getRight()).getOperand().findSubstitutionAndCheck2(((Then) expr).getLeft(), var, null);
                        if (pair.getKey()) {
                            f = true;
                        }
                    } catch (SubstitutionException e) {
                        //break
                        // exitWithMessage("Случилость что-то печальное на строке " + row);
                    }
                }
            }               //<-
            if (!f) {
                if (
                        expr instanceof Then &&
                                ((Then) expr).getLeft() instanceof And &&
                                ((And) ((Then) expr).getLeft()).getRight() instanceof ForAll &&
                                ((ForAll) ((And) ((Then) expr).getLeft()).getRight()).getOperand() instanceof Then) {
                    Expression expr1 = ((Then) expr).getRight();
                    And and = (And) ((Then) expr).getLeft();
                    Then then = (Then) ((ForAll) ((And) ((Then) expr).getLeft()).getRight()).getOperand();
                    Variable var = ((ForAll) and.getRight()).var;
                    Pair<Boolean, Term> pair = null;
                    boolean exit = false;
                    try {
                        pair = expr1.findSubstitutionAndCheck2(and.getLeft(), var, null);
                    } catch (SubstitutionException e) {
                        exit = true;
                    }
                    if (!exit && pair.getKey() && pair.getValue().match(new Zero())) {
                        Pair<Boolean, Term> pair2 = null;
                        try {
                            pair2 = expr1.findSubstitutionAndCheck2(then.getRight(), var, null);
                            if (pair2.getKey() && pair2.getValue().match(new Prime(var))) {
                                f = true;
                            }
                        } catch (SubstitutionException e) {
                            exit = true;
                        }
                    }
                }

            }
            if (!f) {
                for (AxiomScheme scheme : AxiomScheme.values()) {
                    f = scheme.match(expr);
                    if (f) {
                        break;
                    }
                }
            }
            if (!f) {
                for (ArithmeticAxiom axiom : ArithmeticAxiom.values()) {
                    f = axiom.match(expr);
                    if (f) {
                        break;
                    }
                }
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
                if (expr instanceof Then &&
                        ((Then) expr).getRight() instanceof ForAll) {
                    Expression prev = proofed.get(
                            new Then(
                                    ((Then) expr).getLeft(),
                                    ((ForAll) ((Then) expr).getRight()).getOperand()
                            ).toString());
                    Variable var = ((ForAll) ((Then) expr).getRight()).var;
                    boolean cond = (prev != null);
                    if (!cond) break;
                    cond = !((Then) prev).getLeft().getFreeVars().containsKey(var.getName());
                    if (cond) {
                        f = true;
                    }
                }
            }

            if (!f) {
                if (expr instanceof Then &&
                        ((Then) expr).getLeft() instanceof Exists) {
                    Expression prev = proofed.get(
                            new Then(
                                    ((Exists) ((Then) expr).getLeft()).getOperand(),
                                    ((Then) expr).getRight()
                            ).toString());
                    Variable var = ((Exists) ((Then) expr).getLeft()).var;
                    boolean cond = (prev != null);
                    if (!cond) break;
                    cond = !((Then) prev).getRight().getFreeVars().containsKey(var.getName());
                    if (cond) {
                        f = true;
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
