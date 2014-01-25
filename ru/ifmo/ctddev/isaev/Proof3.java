package ru.ifmo.ctddev.isaev;

import ru.ifmo.ctddev.isaev.exception.IncorrectProofException;
import ru.ifmo.ctddev.isaev.exception.LexingException;
import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
import ru.ifmo.ctddev.isaev.helpers.AxiomScheme;
import ru.ifmo.ctddev.isaev.helpers.ContrapositionRule;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.NumExpression;
import ru.ifmo.ctddev.isaev.structure.Variable;
import ru.ifmo.ctddev.isaev.structure.logic.Not;
import ru.ifmo.ctddev.isaev.structure.logic.Or;
import ru.ifmo.ctddev.isaev.structure.logic.Then;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.ifmo.ctddev.isaev.General.*;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 16.11.13
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
public class Proof3 extends Homework {
    Map<String, Variable> vars = new HashMap<>();
    Map<String, Expression> subst = new HashMap<>();
    Deduct2 deduct = new Deduct2();
    List<Expression> tndProofs = new ArrayList<>();
    List<Expression> tnds = new ArrayList<>();

    private List<Expression> getProof(Expression theorem, List<Expression> hypothesis, int currentPosition) throws ProofGeneratingException, IncorrectProofException {
        ArrayList<Expression> proof = new ArrayList<>();
        Expression v = hypothesis.get(currentPosition);
        Expression notV = new Not(v);

        List<Expression> proof1;
        List<Expression> proof2;

        if (currentPosition == hypothesis.size() - 1) {
            deduct.setHypos(hypothesis);
            deduct.setProofed(tnds);
            proof1 = theorem.getParticularProof(hypothesis);
            proof1 = deduct.move1HypoToProof(proof1);
            hypothesis.set(currentPosition, notV);
            deduct.setHypos(hypothesis);
            deduct.setProofed(tnds);
            proof2 = theorem.getParticularProof(hypothesis);
            proof2 = deduct.move1HypoToProof(proof2);
        } else {
            proof1 = getProof(theorem, hypothesis, currentPosition + 1);
            deduct.setHypos(hypothesis.subList(0, currentPosition + 1));
            deduct.setProofed(tnds);
            proof1 = deduct.move1HypoToProof(proof1);
            hypothesis.set(currentPosition, notV);
            proof2 = getProof(theorem, hypothesis, currentPosition + 1);
            deduct.setHypos(hypothesis.subList(0, currentPosition + 1));
            deduct.setProofed(tnds);
            proof2 = deduct.move1HypoToProof(proof2);
        }
        proof.addAll(proof1);
        proof.addAll(proof2);
        hypothesis.set(currentPosition, v);
        HashMap<String, Expression> map = new HashMap<>();
        map.put("1", v);
        map.put("2", notV);
        map.put("3", theorem);
        proof.add(AxiomScheme.SC_8.substitute(map));
        proof.add(new Then(new Then(notV, theorem), new Then(new Or(v, notV), theorem)));
        proof.add(new Then(new Or(v, notV), theorem));
        proof.add(theorem);
        return proof;
    }

    @Override
    public void doSomething() throws IOException, ParsingException, LexingException, IncorrectProofException, ProofGeneratingException {
        Expression theorem = parse(in.readLine());
        vars = theorem.getVars();
        int n = (int) Math.pow(2, vars.size());
        List<Variable> variables = new ArrayList<>(vars.values());
        for (int i = 0; i < n; i++) {
            int k = i;
            for (Expression v : vars.values()) {
                ((Variable) v).currentValue = k % 2 == 1;
                k /= 2;
            }
            theorem = theorem.substituteAndCopy(vars);
            boolean f = theorem.evaluate();
            if (!f) {
                StringBuilder sb = new StringBuilder("Высказывание ложно при ");
                for (int j = 0; j < variables.size(); j++) {
                    sb.append(variables.get(j).name).append("=").append(variables.get(j).currentValue ? "И" : "Л");
                    if (j != variables.size() - 1) {
                        sb.append(", ");
                    }
                }
                out.println(sb);
                out.close();
                System.exit(0);
            }
        }

        for (Expression v : vars.values()) {
            tndProofs.addAll(tertiumNonDatur((Variable) v));
            Expression tnd = new Or(v, new Not(v));
            tnds.add(tnd);
            for (Expression v1 : vars.values()) {
                tnds.add(new Then(tnd, new Then(v1, tnd)));
                tnds.add(new Then(tnd, new Then(new Not(v1), tnd)));
                tnds.add(new Then(v1, tnd));
                tnds.add(new Then(new Not(v1), tnd));
            }
        }
        List<Expression> hypothesis = new ArrayList<Expression>(variables);
        List<Expression> proof = getProof(theorem, hypothesis, 0);

        for (Expression e : tndProofs) {
            out.println(e.toString());
        }
        for (Expression e : tnds) {
            out.println(e.toString());
        }
        for (Expression e : proof) {
            out.println(e.toString());
        }
    }

    private ArrayList<Expression> tertiumNonDatur(Variable v) {
        subst.put("1", v);
        Not notV = new Not(v);
        ArrayList<Expression> result = new ArrayList<>();

        result.add(new Then(new NumExpression(1), new Or(new NumExpression(1), new Not(new NumExpression(1)))).substitute(subst));
        for (ContrapositionRule s : ContrapositionRule.values()) {
            result.add(s.replace(v, new Or(v, notV)));
        }
        result.add(new Then(new Not(new Or(new NumExpression(1), new Not(new NumExpression(1)))), new Not(new NumExpression(1))).substitute(subst));

        result.add(new Then(new Not(new NumExpression(1)), new Or(new NumExpression(1), new Not(new NumExpression(1)))).substitute(subst));
        for (ContrapositionRule s : ContrapositionRule.values()) {
            result.add(s.replace(new Not(v), new Or(v, notV)));
        }
        result.add(new Then(new Not(new Or(new NumExpression(1), new Not(new NumExpression(1)))), new Not(new Not(new NumExpression(1)))).substitute(subst));


        result.add(new Then(new Then(new Not(new Or(new NumExpression(1), new Not(new NumExpression(1)))), new Not(new NumExpression(1))), new Then(new Then(new Not(new Or(new NumExpression(1), new Not(new NumExpression(1)))), new Not(new Not(new NumExpression(1)))), new Not(new Not(new Or(new NumExpression(1), new Not(new NumExpression(1))))))).substitute(subst));
        result.add(new Then(new Then(new Not(new Or(new NumExpression(1), new Not(new NumExpression(1)))), new Not(new Not(new NumExpression(1)))), new Not(new Not(new Or(new NumExpression(1), new Not(new NumExpression(1)))))).substitute(subst));
        result.add(new Not(new Not(new Or(new NumExpression(1), new Not(new NumExpression(1))))).substitute(subst));
        result.add(new Then(new Not(new Not(new Or(new NumExpression(1), new Not(new NumExpression(1))))), new Or(new NumExpression(1), new Not(new NumExpression(1)))).substitute(subst));
        result.add(new Or(new NumExpression(1), new Not(new NumExpression(1))).substitute(subst));

        return result;
    }
}
