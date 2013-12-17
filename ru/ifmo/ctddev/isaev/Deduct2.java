package ru.ifmo.ctddev.isaev;

import ru.ifmo.ctddev.isaev.exception.IncorrectProofException;
import ru.ifmo.ctddev.isaev.exception.LexingException;
import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.helpers.AxiomScheme;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.Then;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public Deduct2() {
    }

    public Deduct2(List<Expression> hypos, Expression alpha) {
        this.hypos = hypos;
        this.hypos.add(alpha);
    }

    public void setHypos(List<Expression> hypos) {
        this.hypos = hypos;
    }

    public void setProofed(List<Expression> proofed) {
        this.proofed = proofed;
    }

    public List<Expression> move1HypoToProof(List<Expression> proof) throws IncorrectProofException {
        List<Expression> result = new ArrayList<>();
        alpha = hypos.remove(hypos.size() - 1);
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
                proofed.add(expr);
            }
            if (!f && expr.match(alpha)) {
                result.addAll(proofAThenA(alpha));
                f = true;
                proofed.add(expr);
            }
            if (!f) {
                for (int i = 0; i < proofed.size(); i++) {
                    for (Expression aProofed : proofed)
                        if (modusPonens(proofed.get(i), aProofed, expr)) {
                            result.add(parse("(1->2)->((1->(2->3))->(1->3))".replace("1", alpha.asString()).replace("2", proofed.get(i).asString()).replace("3", expr.asString())));
                            result.add(parse("(1->(2->3))->(1->3)".replace("1", alpha.asString()).replace("2", proofed.get(i).asString()).replace("3", expr.asString())));
                            result.add(new Then(alpha, expr));
                            f = true;
                        }
                }
                proofed.add(expr);
            }
            if (!f) {
                throw new IncorrectProofException(expr.toString());
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
