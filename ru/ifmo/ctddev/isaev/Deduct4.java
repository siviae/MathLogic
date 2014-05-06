package ru.ifmo.ctddev.isaev;

import javafx.util.Pair;
import ru.ifmo.ctddev.isaev.exception.*;
import ru.ifmo.ctddev.isaev.hardcodedRules.AxiomScheme;
import ru.ifmo.ctddev.isaev.hardcodedRules.ExistsRule;
import ru.ifmo.ctddev.isaev.hardcodedRules.ForAllRule;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.logic.NumExpr;
import ru.ifmo.ctddev.isaev.structure.logic.Then;
import ru.ifmo.ctddev.isaev.structure.predicate.Exists;
import ru.ifmo.ctddev.isaev.structure.predicate.ForAll;
import ru.ifmo.ctddev.isaev.structure.predicate.Term;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Pattern;

import static ru.ifmo.ctddev.isaev.General.*;

/**
 * User: Xottab
 * Date: 09.03.14
 */
public class Deduct4 extends Homework {
    public PrintWriter stat;
    DenialReason denial;
    private List<Expression> hypos = new ArrayList<>();
    private Map<String, Expression> proofed = new HashMap<>();
    private Expression alpha;
    private Set<String> hyposVars = new HashSet<>();
    private Map<String, Expression> map = new HashMap<>();
    private Map<Expression, ArrayList<Expression>> mps = new HashMap<>();
    private boolean success;

    public Deduct4() {
        try {
            stat = new PrintWriter("stat.out");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public Deduct4(List<Expression> hypos, Expression alpha) {
        this.hypos = hypos;
        this.hypos.add(alpha);
    }

    public void setHypos(List<Expression> hypos) {
        this.hypos = new ArrayList<>(hypos);
        this.alpha = this.hypos.remove(hypos.size() - 1);
    }

    public void setProofed(List<Expression> proofed) {
        this.proofed = new HashMap<>();
        for (Expression e : proofed) {
            this.proofed.put(e.toString(), e);
        }
    }

    private void finish(String reason) {
        out.println(reason);
        out.close();
        System.exit(0);
    }

    public List<Expression> move1HypoToProof(List<Expression> proof) throws IncorrectProofException {
        List<Expression> result = new ArrayList<>();
        mps.clear();
        success = false;
        for (Expression e : proofed.values()) {
            result.add(e);
            result.add(new Then(e, new Then(alpha, e)));
            Expression temp = new Then(alpha, e);
            result.add(temp);
            addToMps(mps, e);
            addToMps(mps, temp);
        }
        hyposVars.addAll(alpha.getFreeVars());
        for (Expression e : hypos) {
            hyposVars.addAll(e.getFreeVars());
        }

        for (int l = 0; l < proof.size(); l++) {
            Expression expr = proof.get(l);


            if (row == 5) {
                boolean c = true;
            }

            boolean f = false;
            for (Expression e : hypos) {
                if (e.match(expr)) {
                    f = true;
                    break;
                }
            }
            //something complex is happening here ->
            if (!f) {
                if (expr instanceof Then
                        && ((Then) expr).getLeft() instanceof ForAll) {
                    Term var = ((ForAll) ((Then) expr).getLeft()).var;
                    try {
                        ((ForAll) ((Then) expr).getLeft()).getOperand().setQuantifiers(new HashSet<String>());
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
                        Term temp = null;
                        if (cond) {
                            for (Pair<Term, Term> pair : replaced) {
                                if (temp == null) {
                                    temp = pair.getValue();
                                } else {
                                    if (!(temp.matchAnotherTerm(pair.getValue()))) {
                                        cond = false;
                                        break;
                                    }
                                }
                            }
                        }
                        /*todo проверить все вхождения, а не только последнее*/
                        /*на данный момент мы нашли все замены и проверили, что все они одинаковые
                        * осталось проверить, что ни одна из них не испортила свободное вхождение*/
                        if (temp == null) cond = false;
                        if (cond) {
                            for (Pair<Term, Term> pair : replaced) {
                                Term t = pair.getValue();
                                List<String> names = t.getTermNames();
                                for (String s : names) {
                                    if (t.quantifiers.contains(s)) {
                                        cond = false;
                                        DenialReason.ERROR_1.create(l + 1, String.valueOf(temp), ((ForAll) ((Then) expr).getLeft()).getOperand().toString(), var.getName());
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
            /*todo подумать насчёт проверки свободных переменных из допущений*/
            if (!f) {
                if (expr instanceof Then
                        && ((Then) expr).getRight() instanceof Exists) {
                    Term var = ((Exists) ((Then) expr).getRight()).var;
                    try {
                        ((Exists) ((Then) expr).getRight()).getOperand().setQuantifiers(new HashSet<String>());
                        int freeCount = ((Exists) ((Then) expr).getRight()).getOperand().markFreeVariableOccurences(var.getName());
                        Set<Pair<Term, Term>> replaced = ((Then) expr).getLeft().getReplacedVariableOccurences(((Exists) ((Then) expr).getRight()).getOperand());
                        boolean cond = true;
                        if (freeCount == 0) {
                            cond = false;
                            if (((Exists) ((Then) expr).getRight()).getOperand().treeEquals(((Then) expr).getLeft())) {
                                f = true;
                            } //ничего не подставляем, но деревья одинаковые
                        }
                        Term temp = null;
                        if (cond) {
                            for (Pair<Term, Term> pair : replaced) {
                                if (temp == null) {
                                    temp = pair.getValue();
                                } else {
                                    if (!(temp.matchAnotherTerm(pair.getValue()))) {
                                        cond = false;
                                        break;
                                    }
                                }
                            }
                        }
                        /*todo проверить все вхождения, а не только последнее*/
                        /*на данный момент мы нашли все замены и проверили, что все они одинаковые
                        * осталось проверить, что ни одна из них не испортила свободное вхождение*/
                        if (temp == null) cond = false;
                        if (cond) {
                            for (Pair<Term, Term> pair : replaced) {
                                Term t = pair.getValue();
                                List<String> names = t.getTermNames();
                                for (String s : names) {
                                    if (t.quantifiers.contains(s)) {
                                        cond = false;
                                        DenialReason.ERROR_1.create(l + 1, String.valueOf(temp), ((Exists) ((Then) expr).getRight()).getOperand().toString(), var.getName());
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
            }               //<-
            if (!f) {
                for (AxiomScheme scheme : AxiomScheme.values()) {
                    if (scheme.match(expr)) {
                        f = true;
                        break;
                    }
                }
            }
            if (f) {
                result.add(expr);
                result.add(new Then(expr, new Then(alpha, expr)));
                Expression temp = new Then(alpha, expr);
                result.add(temp);
            }
            if (!f && expr.treeEquals(alpha)) {
                result.addAll(proofAThenA(alpha));
                f = true;
            }
            if (proofed.containsKey(expr.toString())) {
                f = true;
            }

            if (!f) {
                if (mps.get(expr) != null) {
                    for (Expression e : mps.get(expr)) {
                        if (proofed.get(e.toString()) != null) {
                            map.put("1", alpha);
                            map.put("2", e);
                            map.put("3", expr);
                            result.add(
                                    new Then(
                                            new Then(
                                                    new NumExpr(1),
                                                    new NumExpr(2)),
                                            new Then(
                                                    new Then(
                                                            new NumExpr(1),
                                                            new Then(
                                                                    new NumExpr(2),
                                                                    new NumExpr(3))),
                                                    new Then(
                                                            new NumExpr(1),
                                                            new NumExpr(3)))).substitute(map));
                            result.add(
                                    new Then(
                                            new Then(
                                                    new NumExpr(1),
                                                    new Then(
                                                            new NumExpr(2),
                                                            new NumExpr(3))),
                                            new Then(
                                                    new NumExpr(1),
                                                    new NumExpr(3))).substitute(map));
                            result.add(new Then(alpha, expr));
                            f = true;
                            break;
                        }
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
                        cond = cond && !((Then) prev).getLeft().getFreeVars().contains(var.getName());
                        if (!cond) {
                            DenialReason.ERROR_2.create(l + 1, var.getName(), ((Then) expr).getLeft().toString());
                        }
                        cond = cond && !hyposVars.contains(var.getName());
                        if (!cond) {
                            DenialReason.ERROR_3.create(l + 1, "правило", var.getName(), searchHypoByVar(var).toString(), prev.toString(), expr.toString());
                        }
                        if (cond) {
                            for (ForAllRule rule : ForAllRule.values()) {
                                if (rule == ForAllRule.R_80) {
                                    boolean k = true;
                                }
                                result.add(rule.replace(alpha,
                                        ((Then) expr).getLeft(),
                                        ((ForAll) ((Then) expr).getRight()).getOperand()));
                            }
                            f = true;
                        }
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
                    if (cond) {
                        cond = cond && !((Then) prev).getRight().getFreeVars().contains(var.getName());
                        if (!cond) {
                            DenialReason.ERROR_2.create(l + 1, var.getName(), ((Then) expr).getRight().toString());
                        }
                        cond = cond && !hyposVars.contains(var.getName());
                        if (!cond) {
                            DenialReason.ERROR_3.create(l + 1, "правило", var.getName(), searchHypoByVar(var).toString(), prev.toString(), expr.toString());
                        }
                        if (cond) {
                            for (ExistsRule rule : ExistsRule.values()) {
                                result.add(rule.replace(alpha,
                                        ((Exists) ((Then) expr).getLeft()).getOperand(),
                                        ((Then) expr).getRight()));
                            }
                            f = true;
                        }
                    }
                }
            }


            if (!f) {
                for (Expression e : proofed.values()) {
                    out.println(e.asString());
                }
                out.println("до этого всё было ок");
                finish("Не получилось доказать: " + expr.asString());
                break;
            } else {
                proofed.put(expr.toString(), expr);
                addToMps(mps, expr);
                addToMps(mps, new Then(alpha, expr));
            }
            success = true;
        }
        return result;
    }

    private Expression searchHypoByVar(Term v) {
        if (alpha.getFreeVars().contains(v.getName())) return alpha;
        for (Expression e : hypos) {
            if (e.getFreeVars().contains(v.getName())) return e;
        }
        return null;
    }

    @Override
    public void doSomething() throws IOException, ParsingException, LexingException, IncorrectProofException {
        String[] temp = in.readLine().split(Pattern.quote("|-"));
        if (temp.length > 2) {
            throw new IOException("more than one |- in first line");
        }
        String s = temp[0];
        int l = 0;
        int r = 0;
        while (l < s.length()) {
            r++;
            Expression tempExpr;
            try {
                String ss = s.substring(l, r);
                tempExpr = parseExcept(ss);
            } catch (Exception e) {
                continue;
            }
            if (r == s.length() || s.charAt(r) == ',') {
                l = r + 1;
                r = l;
                hypos.add(tempExpr);
            }
        }

        /*for (String value : s) {
            hypos.add(parse(value));
        } -*/
        alpha = hypos.remove(hypos.size() - 1);
        List<Expression> proof = new ArrayList<>();
        String s1 = in.readLine();
        while (s1 != null && !s1.replace("\\s+", "").isEmpty()) {
            Expression e = parse(s1);
            proof.add(e);
            s1 = in.readLine();
        }
        List<Expression> newProof = move1HypoToProof(proof);
        if (success) for (Expression e : newProof) {
            out.println(e.asString());
        }
    }

    public boolean haveZeroHypos() {
        return hypos.size() == 0;
    }

}
