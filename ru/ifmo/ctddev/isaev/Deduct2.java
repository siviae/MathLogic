package ru.ifmo.ctddev.isaev;

import ru.ifmo.ctddev.isaev.exception.IncorrectProofException;
import ru.ifmo.ctddev.isaev.exception.LexingException;
import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.helpers.AxiomScheme;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.NumExpression;
import ru.ifmo.ctddev.isaev.structure.logic.Then;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static ru.ifmo.ctddev.isaev.General.*;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 10.11.13
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
public class Deduct2 extends Homework {

    private List<Expression> hypos = new ArrayList<>();
    private List<Expression> proofed = new ArrayList<>();
    private Expression alpha;
    private Map<String, Expression> map = new HashMap<>();

    public Deduct2() {
    }

    public Deduct2(List<Expression> hypos, Expression alpha) {
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
        List<Expression> result = new ArrayList<>();
        for (Expression expr : proof) {
            boolean f = false;
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
            if (f) {
                result.add(expr);
                result.add(new Then(expr, new Then(alpha, expr)));
                result.add(new Then(alpha, expr));
            }
            if (!f && expr.match(alpha)) {
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
                            map.put("1", alpha);
                            map.put("2", proofed.get(i));
                            map.put("3", expr);
                            result.add(
                                    new Then(
                                            new Then(
                                                    new NumExpression(1),
                                                    new NumExpression(2)),
                                            new Then(
                                                    new Then(
                                                            new NumExpression(1),
                                                            new Then(
                                                                    new NumExpression(2),
                                                                    new NumExpression(3))),
                                                    new Then(
                                                            new NumExpression(1),
                                                            new NumExpression(3)))).substitute(map));
                            result.add(
                                    new Then(
                                            new Then(
                                                    new NumExpression(1),
                                                    new Then(
                                                            new NumExpression(2),
                                                            new NumExpression(3))),
                                            new Then(
                                                    new NumExpression(1),
                                                    new NumExpression(3))).substitute(map));
                            result.add(new Then(alpha, expr).substitute(map));
                            f = true;
                            break;
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

        return result;
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
        for (Expression e : newProof) {
            out.println(e.asString());
        }
    }

    public boolean haveZeroHypos() {
        return hypos.size() == 0;
    }
}
