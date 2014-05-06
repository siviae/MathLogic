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
import java.util.*;

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
            if (row == /*7497*/47) {
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
                if (expr instanceof Then
                        && ((Then) expr).getLeft() instanceof ForAll) {
                    Term var = ((ForAll) ((Then) expr).getLeft()).var;
                    try {
                        ((ForAll) ((Then) expr).getLeft()).getOperand().setQuantifiers(new HashSet<>());
                        ((Then) expr).getRight().setQuantifiers(new HashSet<>());
                        int freeCount = ((ForAll) ((Then) expr).getLeft()).getOperand().markFreeVariableOccurences(var.getName());
                        Set<Pair<Term, Term>> replaced = ((Then) expr).getRight().getReplacedVariableOccurences(((ForAll) ((Then) expr).getLeft()).getOperand());
                        //trees are matching
                        boolean cond = true;
                        if (freeCount == 0) {
                            cond = false;
                            if (((ForAll) ((Then) expr).getLeft()).getOperand().treeEquals(((Then) expr).getRight())) {
                                f = true;
                            } //todo вот это весьма сомнительно
                        }
                        Term term = null;
                        if (cond) {
                            for (Pair<Term, Term> pair : replaced) {
                                if (term == null) {
                                    term = pair.getValue();
                                } else {
                                    if (!(term.matchAnotherTerm(pair.getValue()))) {
                                        cond = false;
                                        break;
                                    }
                                }
                            }
                        }
                        /*todo проверить все вхождения, а не только последнее*/
                        /*на данный момент мы нашли все замены и проверили, что все они одинаковые
                        * осталось проверить, что ни одна из них не испортила свободное вхождение*/
                        if (term == null) cond = false;
                        if (cond) {
                            for (Pair<Term, Term> pair : replaced) {
                                Term t = pair.getValue();
                                List<String> names = t.getTermNames();
                                for (String s : names) {
                                    //System.out.println(row);
                                    if (t.quantifiers.contains(s)) {
                                        cond = false;
                                        DenialReason.ERROR_1.create(row + 1, String.valueOf(term), ((ForAll) ((Then) expr).getLeft()).getOperand().toString(), var.getName());
                                        break;
                                    }
                                }
                            }
                        }
                        if (cond) {
                            f = true;
                        }
                    } catch (TreeMismatchException e) {
                        //trees are not equal
                    }
                }
            }
            if (!f) {
                if (expr instanceof Then
                        && ((Then) expr).getRight() instanceof Exists) {
                    Term var = ((Exists) ((Then) expr).getRight()).var;
                    try {
                        ((Exists) ((Then) expr).getRight()).getOperand().setQuantifiers(new HashSet<String>());
                        ((Then) expr).getRight().setQuantifiers(new HashSet<String>());
                        int freeCount = ((Exists) ((Then) expr).getRight()).getOperand().markFreeVariableOccurences(var.getName());
                        Set<Pair<Term, Term>> replaced = ((Then) expr).getLeft().getReplacedVariableOccurences(((Exists) ((Then) expr).getRight()).getOperand());
                        boolean cond = true;
                        if (freeCount == 0) {
                            cond = false;
                            if (((Exists) ((Then) expr).getRight()).getOperand().treeEquals(((Then) expr).getLeft())) {
                                f = true;
                            } //ничего не подставляем, но деревья одинаковые
                        }
                        Term term = null;
                        if (cond) {
                            for (Pair<Term, Term> pair : replaced) {
                                if (term == null) {
                                    term = pair.getValue();
                                } else {
                                    if (!(term.matchAnotherTerm(pair.getValue()))) {
                                        cond = false;
                                        break;
                                    }
                                }
                            }
                        }
                        /*todo проверить все вхождения, а не только последнее*/
                        /*на данный момент мы нашли все замены и проверили, что все они одинаковые
                        * осталось проверить, что ни одна из них не испортила свободное вхождение*/
                        if (term == null) cond = false;
                        if (cond) {
                            for (Pair<Term, Term> pair : replaced) {
                                Term t = pair.getValue();
                                List<String> names = t.getTermNames();
                                for (String s : names) {
                                    if (t.quantifiers.contains(s)) {
                                        cond = false;
                                        DenialReason.ERROR_1.create(row + 1, String.valueOf(term), ((Exists) ((Then) expr).getRight()).getOperand().toString(), var.getName());
                                        break;
                                    }
                                }
                            }
                        }
                        if (cond) {
                            f = true;
                        }
                    } catch (TreeMismatchException e) {
                        //trees are not equal
                    }
                }
            }
            if (!f) {
                if (
                        expr instanceof Then &&
                                ((Then) expr).getLeft() instanceof And &&
                                ((And) ((Then) expr).getLeft()).getRight() instanceof ForAll &&
                                ((ForAll) ((And) ((Then) expr).getLeft()).getRight()).getOperand() instanceof Then) {
                    Expression expr1 = ((Then) expr).getRight();
                    And and = (And) ((Then) expr).getLeft();
                    Then then = (Then) ((ForAll) ((And) ((Then) expr).getLeft()).getRight()).getOperand();
                    Expression original = then.getLeft();
                    Term var = ((ForAll) and.getRight()).var;
                    original.setQuantifiers(new HashSet<String>());
                    and.getLeft().setQuantifiers(new HashSet<String>());
                    int freeCount = original.markFreeVariableOccurences(var.getName());
                    try {
                        Set<Pair<Term, Term>> replaced = and.getLeft().getReplacedVariableOccurences(original);
                        boolean cond = true;
                        if (freeCount == 0) {
                            cond = false;
                            if (and.getLeft().treeEquals(original)) {
                                f = true;
                            } //ничего не подставляем, но деревья одинаковые
                        }
                        Term term = null;
                        if (cond) {
                            for (Pair<Term, Term> pair : replaced) {
                                if (term == null) {
                                    term = pair.getValue();
                                } else {
                                    if (!(term.matchAnotherTerm(pair.getValue()))) {
                                        cond = false;
                                        break;
                                    }
                                }
                            }
                        }
                        if (cond && !term.match(new Zero())) {
                            cond = false;
                        }
                        if (cond && !original.treeEquals(expr1)) {
                            cond = false;
                        }
                        then.getRight().setQuantifiers(new HashSet<String>());
                        replaced = then.getRight().getReplacedVariableOccurences(original);
                        if (freeCount == 0) {
                            cond = false;
                            f = true;//мы ничего не подставляем, но деревья одинаковые
                        }
                        term = null;
                        if (cond) {
                            for (Pair<Term, Term> pair : replaced) {
                                if (term == null) {
                                    term = pair.getValue();
                                } else {
                                    if (!(term.matchAnotherTerm(pair.getValue()))) {
                                        cond = false;
                                        break;
                                    }
                                }
                            }
                        }

                        if (cond && !term.match(new Prime(var))) {
                            cond = false;
                        }
                        if (cond) f = true;
                    } catch (TreeMismatchException e) {

                    }
                }
            }
            //проверка на новые правила вывода
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
                    if (cond) {
                        Set<String> freeVars = ((Then) prev).getLeft().getFreeVars();
                        if (freeVars.contains(var.getName())) {
                            DenialReason.ERROR_2.create(row + 1, var.getName(), ((Then) expr).getLeft().toString());
                        }
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
                    if (cond)
                        if (((Then) prev).getRight().getFreeVars().contains(var.getName())) {
                            DenialReason.ERROR_2.create(row + 1, var.getName(), ((Then) expr).getRight().toString());
                        }
                    if (cond) {
                        f = true;
                    }
                }
            }


            if (!f) {
                out.println("Доказательство некорректно начиная с " + row + " высказывания: " + temp);
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
