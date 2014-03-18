package ru.ifmo.ctddev.isaev;

import ru.ifmo.ctddev.isaev.exception.IncorrectProofException;
import ru.ifmo.ctddev.isaev.exception.LexingException;
import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
import ru.ifmo.ctddev.isaev.helpers.AxiomScheme;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.NumExpr;
import ru.ifmo.ctddev.isaev.structure.logic.Then;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static ru.ifmo.ctddev.isaev.General.*;

/**
 * User: Xottab
 * Date: 09.03.14
 */
public class Deduct4 extends Homework {

    public PrintWriter stat;
    private int k1;
    private int k2;
    private int k3;
    private List<Expression> hypos = new ArrayList<>();
    private List<Expression> proofed = new ArrayList<>();
    private Expression alpha;
    private Map<String, Expression> map = new HashMap<>();

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

    public void setAlpha(Expression alpha) {
        this.alpha = alpha;
    }

    public void setProofed(List<Expression> proofed) {
        this.proofed = new ArrayList<>(proofed);
    }

    public List<Expression> move1HypoToProof(List<Expression> proof) throws IncorrectProofException {
        k1 = 0;
        k2 = 0;
        k3 = 0;
        List<Expression> result = new ArrayList<>();
        for (Expression expr : proof) {
            boolean f = false;
            //todo remove stupid copypaste
            for (Expression e : hypos) {
                if (e.match(expr)) {
                    f = true;
                    break;
                }
            }
            if (!f) {
                for (AxiomScheme scheme : AxiomScheme.values()) {
                    if (scheme.match(expr)) {
                        f = true;
                        break;
                    }
                }
            }
            if (!f) {
                if (General.matchForAllPredicateAxiom(expr)) {
                    f = true;
                }
            }
            if (!f && General.matchExistsPredicateAxiom(expr)) {
                f = true;

            }
            if (f) {
                k1++;
                result.add(expr);
                result.add(new Then(expr, new Then(alpha, expr)));
                result.add(new Then(alpha, expr));
            }


            if (!f && expr.match(alpha)) {
                k2++;
                result.addAll(proofAThenA(alpha));
                f = true;
            }
            if (!f) {
                for (int i = proofed.size() - 1; i >= 0; i--) {
                    if (proofed.get(i).equals(expr)) {
                        f = true;
                        break;
                    }
                    for (int j = proofed.size() - 1; j >= 0; j--) {
                        Expression aProofed = proofed.get(j);
                        if (modusPonens(proofed.get(i), aProofed, expr)) {
                            k3++;
                            map.put("1", alpha);
                            map.put("2", proofed.get(i));
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

                    String[] strings = null;
                    if (!f && forAllRule(proofed.get(i), expr)) {
                        f = true;
                        strings = GiantStrings.FORALL_DEDUCT
                                .replace("{A}", alpha.asString())
                                .replace("{B}", ((Then) proofed.get(i)).getLeft().asString())
                                .replace("{C}", ((Then) proofed.get(i)).getRight().asString())
                                .split("\\n");
                    }
                    if (!f && existsRule(proofed.get(i), expr)) {
                        f = true;
                        strings = GiantStrings.FORALL_DEDUCT
                                .replace("{A}", alpha.asString())
                                .replace("{B}", ((Then) proofed.get(i)).getLeft().asString())
                                .replace("{C}", ((Then) proofed.get(i)).getRight().asString())
                                .split("\\n");
                    }
                    if (f) {
                        for (String s : strings) {
                            result.add(parse(s));
                        }
                    }
                }
            }
            if (!f) {
                for (Expression e : proofed) {
                    out.println(e.asString());
                }
                out.println("до этого всё было ок");
                out.println("Не получилось доказать: " + expr.asString());
                throw new IncorrectProofException(expr.toString());
            } else {
                proofed.add(expr);
            }
        }
        //todo end
        stat.println("Axiom: " + k1);
        stat.println("alpha: " + k2);
        stat.println("MP: " + k3);
        stat.println();
        return result;
    }

    @Override
    public void doSomething() throws IOException, ParsingException, LexingException, IncorrectProofException, ProofGeneratingException {
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
        for (Expression e : newProof) {
            out.println(e.asString());
        }


       /* List<Expression> newProof = move1HypoToProof(proof);
        for (Expression e : newProof) {
            out.println(e.asString());
        }*/
    }
}
