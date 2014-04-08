package ru.ifmo.ctddev.isaev;

import javafx.util.Pair;
import ru.ifmo.ctddev.isaev.exception.IncorrectProofException;
import ru.ifmo.ctddev.isaev.exception.LexingException;
import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.exception.SubstitutionException;
import ru.ifmo.ctddev.isaev.hardcodedRules.AxiomScheme;
import ru.ifmo.ctddev.isaev.hardcodedRules.ExistsRule;
import ru.ifmo.ctddev.isaev.hardcodedRules.ForAllRule;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.logic.NumExpr;
import ru.ifmo.ctddev.isaev.structure.logic.Then;
import ru.ifmo.ctddev.isaev.structure.logic.Variable;
import ru.ifmo.ctddev.isaev.structure.predicate.Exists;
import ru.ifmo.ctddev.isaev.structure.predicate.ForAll;

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
        hyposVars.addAll(alpha.getFreeVars().keySet());
        for (Expression e : hypos) {
            hyposVars.addAll(e.getFreeVars().keySet());
        }

        for (int l = 0; l < proof.size(); l++) {
            Expression expr = proof.get(l);

            boolean f = false;
            for (Expression e : hypos) {
                if (e.treeEquals(expr)) {
                    f = true;
                    break;
                }
            }
            //something complex is happening here ->
            if (!f) {
                if (expr instanceof Then
                        && ((Then) expr).getLeft() instanceof ForAll
                        /*&& ((ForAll) ((Then) expr).getLeft()).getOperand().match(((Then) expr).getRight())*/) {
                    Variable var = ((ForAll) ((Then) expr).getLeft()).var;
                    try {
                        Pair<Boolean, Variable> pair = ((ForAll) ((Then) expr).getLeft()).getOperand().findSubstitutionAndCheck(((Then) expr).getRight(), var, null);
                        if (pair.getKey()) {
                            f = true;
                        } else {
                            if (pair.getValue() != null) {
                                DenialReason.ERROR_1.create(l + 1, pair.getValue().toString(), ((ForAll) ((Then) expr).getLeft()).getOperand().toString(), var.getName());
                            }
                        }
                    } catch (SubstitutionException e) {
                        //  break;
                    }
                }
            }
            if (!f) {
                if (expr instanceof Then
                        && ((Then) expr).getRight() instanceof Exists
                        /*&& ((Exists) ((Then) expr).getRight()).getOperand().match(((Then) expr).getLeft())*/) {
                    Variable var = ((Exists) ((Then) expr).getRight()).var;
                    try {
                        Pair<Boolean, Variable> pair = ((Exists) ((Then) expr).getRight()).getOperand().findSubstitutionAndCheck(((Then) expr).getLeft(), var, null);
                        if (pair.getKey()) {
                            f = true;
                            // break;
                        } else {
                            if (pair.getValue() != null) {
                                DenialReason.ERROR_1.create(l + 1, pair.getValue().toString(), ((Then) expr).getLeft().toString(), var.getName());
                            }/* else {
                                break;
                            }*/
                        }
                    } catch (SubstitutionException e) {
                        //break;
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
                    Variable var = ((ForAll) ((Then) expr).getRight()).var;
                    boolean cond = (prev != null);
                    if (!cond) break;
                    cond = !((Then) prev).getLeft().getFreeVars().containsKey(var.getName());
                    if (!cond) {
                        DenialReason.ERROR_2.create(l + 1, var.getName(), ((Then) expr).getLeft().toString());
                    }
                    cond = cond && !hyposVars.contains(var.getName());
                    if (!cond) {
                        DenialReason.ERROR_3.create(l + 1, "правило", var.getName(), searchHypoByVar(var).toString());
                    }
                    if (cond) {
                        for (ForAllRule rule : ForAllRule.values()) {
                            result.add(rule.replace(alpha,
                                    ((Then) expr).getLeft(),
                                    ((Then) expr).getRight()));
                        }
                        f = true;
                    }
                }
            }

            if (l == 73) {
                boolean k = true;
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
                    if (!cond) {
                        DenialReason.ERROR_2.create(l + 1, var.getName(), ((Then) expr).getRight().toString());
                    }
                    cond = cond && !hyposVars.contains(var.getName());
                    if (!cond) {
                        DenialReason.ERROR_3.create(l + 1, "правило", var.getName(), searchHypoByVar(var).toString());
                    }
                    if (cond) {
                        for (ExistsRule rule : ExistsRule.values()) {
                            result.add(rule.replace(alpha,
                                    ((Then) expr).getLeft(),
                                    ((Then) expr).getRight()));
                        }
                        f = true;
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

    private Expression searchHypoByVar(Variable v) {
        if (alpha.getFreeVars().containsKey(v.toString())) return alpha;
        for (Expression e : hypos) {
            if (e.getFreeVars().containsKey(v.toString())) return e;
        }
        return null;
    }

    @Override
    public void doSomething() throws IOException, ParsingException, LexingException, IncorrectProofException {
        String[] temp = in.readLine().split(Pattern.quote("|-"));
        if (temp.length > 2) {
            throw new IOException("more than one |- in first line");
        }
        String[] s = temp[0].split(",");
        for (String value : s) {
            hypos.add(parse(value));
        }
        alpha = hypos.remove(hypos.size() - 1);
        List<Expression> proof = new ArrayList<>();
        String s1 = in.readLine();
        while (s1 != null && !s1.replace("\\s+", "").isEmpty()) {
            proof.add(parse(s1));
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

    private enum DenialReason {
        ERROR_1("терм %s не свободен для подстановки в формулу %s вместо переменной %s."),
        ERROR_2("переменная %s входит свободно в формулу %s."),
        ERROR_3("используется %s с квантором по переменной %s, " +
                "входящей свободно в допущение %s.");
        String reason;

        DenialReason(String reason) {
            this.reason = reason;
        }

        void create(int row, String... params) {
            out.println("Вывод некорректен, начиная с формулы № " + row + "\n");
            reason = String.format(reason, params);
            out.println(reason);
            out.close();
            System.exit(0);
        }

    }
}
