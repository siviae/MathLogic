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
            if (row == /*7497*/265) {
                boolean c = true;
            }
            Expression expr = parse(temp);
            try {
                if (proofed.containsKey(expr.toString())) {
                    f = true;
                }
            } catch (NullPointerException e) {
                System.out.println("row is " + row);
                System.out.println("string is " + temp);
                e.printStackTrace();
            }
            if (!f) {
                if (expr instanceof Then
                        && ((Then) expr).getLeft() instanceof ForAll
                        /*&& ((ForAll) ((Then) expr).getLeft()).getOperand().match(((Then) expr).getRight())*/) {
                    Term var = ((ForAll) ((Then) expr).getLeft()).var;
                    try {
                        Pair<Boolean, Term> pair = ((ForAll) ((Then) expr).getLeft()).getOperand().findSubstitutionAndCheck(((Then) expr).getRight(), var, null);
                        if (pair.getKey()) {
                            f = true;
                        } else {
                            if (pair.getValue() != null) {
                                DenialReason.ERROR_1.create(row + 1, pair.getValue().toString(), ((ForAll) ((Then) expr).getLeft()).getOperand().toString(), var.getName());
                            }
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
                    Term var = ((Exists) ((Then) expr).getRight()).var;
                    try {
                        Pair<Boolean, Term> pair = ((Exists) ((Then) expr).getRight()).getOperand().findSubstitutionAndCheck(((Then) expr).getLeft(), var, null);
                        if (pair.getKey()) {
                            f = true;
                            // break;
                        } else {
                            if (pair.getValue() != null) {
                                DenialReason.ERROR_1.create(row + 1, pair.getValue().toString(), ((Then) expr).getLeft().toString(), var.getName());
                            }/* else {
                                break;
                            }*/
                        }
                    } catch (SubstitutionException e) {
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
                    Term var = ((ForAll) and.getRight()).var;
                    Pair<Boolean, Term> pair = null;
                    boolean exit = false;
                    try {
                        pair = expr1.findSubstitutionAndCheck(and.getLeft(), var, null);
                    } catch (SubstitutionException e) {
                        exit = true;
                    }
                    if (!exit && pair.getKey() && pair.getValue() instanceof Zero) {
                        Pair<Boolean, Term> pair2 = null;
                        try {
                            pair2 = expr1.findSubstitutionAndCheck(then.getRight(), var, null);
                            if (pair2.getKey() &&
                                    pair2.getValue() instanceof Prime &&
                                    ((Prime) pair2.getValue()).getOperand().match(var)) {
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
                    Term var = ((ForAll) ((Then) expr).getRight()).var;
                    boolean cond = (prev != null);
                    if (!cond) break;
                    cond = !((Then) prev).getLeft().getFreeVars().containsKey(var.getName());
                    if (!cond) {
                        DenialReason.ERROR_2.create(row + 1, var.getName(), ((Then) expr).getLeft().toString());
                    }
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
                    Term var = ((Exists) ((Then) expr).getLeft()).var;
                    boolean cond = (prev != null);
                    if (!cond) break;
                    cond = !((Then) prev).getRight().getFreeVars().containsKey(var.getName());
                    if (!cond) {
                        DenialReason.ERROR_2.create(row + 1, var.getName(), ((Then) expr).getRight().toString());
                    }
                    if (cond) {
                        f = true;
                    }
                }
            }


            if (!f) {
                System.out.println("Доказательство некорректно начиная с " + row + " высказывания: " + temp);
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
