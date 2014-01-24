package ru.ifmo.ctddev.isaev;

import ru.ifmo.ctddev.isaev.exception.IncorrectProofException;
import ru.ifmo.ctddev.isaev.exception.LexingException;
import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
import ru.ifmo.ctddev.isaev.helpers.AxiomScheme;
import ru.ifmo.ctddev.isaev.helpers.ContrapositionRule;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.Variable;
import ru.ifmo.ctddev.isaev.structure.logic.Not;
import ru.ifmo.ctddev.isaev.structure.logic.Or;
import ru.ifmo.ctddev.isaev.structure.logic.Then;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static ru.ifmo.ctddev.isaev.General.*;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 16.11.13
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
public class Proof3 extends Homework {
    HashMap<String, Variable> vars = new HashMap<>();
    Deduct2 deduct;

    private List<Expression> getProof(Expression theorem, List<Expression> hypothesis, int currentPosition) throws ProofGeneratingException, IncorrectProofException {
        List<Expression> proof = new LinkedList<>();
        Expression v = hypothesis.get(currentPosition);
        Expression notV = new Not(v);
        List<Expression> proof1;
        List<Expression> proof2;

        if (currentPosition == hypothesis.size() - 1) {
            deduct.setHypos(hypothesis);
            proof1 = deduct.move1HypoToProof(theorem.getParticularProof(hypothesis));
            hypothesis.set(currentPosition, notV);
            deduct.setHypos(hypothesis);
            proof2 = deduct.move1HypoToProof(theorem.getParticularProof(hypothesis));
        } else {
            deduct.setHypos(hypothesis);
            proof1 = deduct.move1HypoToProof(getProof(theorem, hypothesis, currentPosition + 1));
            hypothesis.set(currentPosition, notV);
            deduct.setHypos(hypothesis);
            proof2 = deduct.move1HypoToProof(getProof(theorem, hypothesis, currentPosition + 1));
        }
        proof.addAll(proof1);
        proof.addAll(proof2);
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
            theorem = theorem.substitute(vars);
            boolean f = theorem.evaluate();
            if (!f) {
                StringBuilder sb = new StringBuilder("Высказывание ложно при ");
                for (int j = 0; j < variables.size(); j++) {
                    sb.append(variables.get(j).token).append("=").append(variables.get(j).currentValue ? "И" : "Л");
                    if (j != variables.size() - 1) {
                        sb.append(", ");
                    }
                }
                out.println(sb);
                out.close();
                System.exit(0);
            }
        }
        List<ArrayList<Expression>> tnd = new ArrayList<>();

        for (Expression v : vars.values()) {
            tnd.add(tertiumNonDatur((Variable) v));
        }
        boolean f = true;

        for (ArrayList<Expression> arr : tnd) {
            for (Expression e : arr) {
                out.println(e.asString());
            }
        }
        List<Expression> hypothesis = new ArrayList<Expression>(variables);
        List<Expression> proof = getProof(theorem, hypothesis, 0);
        for (Expression e : proof) {
            out.println(e.toString());
        }
    }

    private ArrayList<Expression> tertiumNonDatur(Variable v) {
        Not notV = new Not(v);
        ArrayList<Expression> result = new ArrayList<>();
        result.add(parse("A->A|!A".replace("A", v.token)));

        for (ContrapositionRule s : ContrapositionRule.values()) {
            result.add(s.replace(v, new Or(v, notV)));
        }
        result.add(parse("!(A|!A)->!A".replace("A", v.token)));

        result.add(parse("!A->A|!A".replace("A", v.token)));
        for (ContrapositionRule s : ContrapositionRule.values()) {
            result.add(s.replace(new Not(v), new Or(v, notV)));
        }
        result.add(parse("!(A|!A)->!!A".replace("A", v.token)));


        result.add(parse("(!(A|!A)->!A)->(!(A|!A)->!!A)->(!!(A|!A))".replace("A", v.token)));
        result.add(parse("(!(A|!A)->!!A)->!!(A|!A)".replace("A", v.token)));
        result.add(parse("!!(A|!A)".replace("A", v.token)));
        result.add(parse("!!(A|!A)->(A|!A)".replace("A", v.token)));
        result.add(parse("A|!A".replace("A", v.token)));

        return result;
    }
}
