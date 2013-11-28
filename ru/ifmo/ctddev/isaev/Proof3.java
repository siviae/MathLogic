package ru.ifmo.ctddev.isaev;

import ru.ifmo.ctddev.isaev.exception.LexingException;
import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.structure.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 16.11.13
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
public class Proof3 extends Homework {
    HashMap<String, Expression> vars = new HashMap<>();
    Deduct2 deduct;

    private void getVars(Expression e) {
        if (e instanceof Variable && !vars.containsKey(((Variable) e).token)) {
            vars.put(((Variable) e).token, (Variable) e);
        } else if (e instanceof LogicalUnary) {
            getVars(((LogicalUnary) e).operand);
        } else if (e instanceof LogicalBinary) {
            getVars(((LogicalBinary) e).left);
            getVars(((LogicalBinary) e).right);
        }
    }

    @Override
    public void doSomething() throws IOException, ParsingException, LexingException {
        Expression theorem = parse(in.readLine());
        getVars(theorem);
        int n = (int) Math.pow(2, vars.size());
        ArrayList<Expression> variables = new ArrayList<>(vars.values());
        for (int i = 0; i < n; i++) {
            int k = i;
            for (Expression v : vars.values()) {
                ((Variable) v).value = k % 2 == 1;
                k /= 2;
            }
            theorem = theorem.substitute(vars);
            boolean f = theorem.evaluate();
            if (!f) {
                StringBuilder sb = new StringBuilder("Высказывание ложно при ");
                for (int j = 0; j < variables.size(); j++) {
                    sb.append(((Variable) variables.get(j)).token).append("=").append(((Variable) variables.get(j)).value ? "И" : "Л");
                    if (j != variables.size() - 1) {
                        sb.append(", ");
                    }
                }
                out.println(sb);
                out.close();
                System.exit(0);
            }
        }
        List<Expression> proof = new ArrayList<>();
    //    proof.add(theorem);
        List<ArrayList<Expression>> tnd = new ArrayList<>();

        for (Expression v : vars.values()) {
            tnd.add(tertiumNonDatur((Variable) v));
        }
        boolean f = true;
        while (variables.size() > 0) {
            Variable v = (Variable) variables.remove(variables.size() - 1);
            Expression notV = new LogicalNot(v);
            deduct = new Deduct2(variables, v);
            List<Expression> proof1;
            List<Expression> proof2;
            if (f) {
                proof.add(new LogicalThen(v, theorem));
                proof.add(new LogicalThen(notV, theorem));
                f = false;
            } else {
                proof1 = deduct.move1HypoToProof(proof);
                deduct.setAlpha(notV);
                proof2 = deduct.move1HypoToProof(proof);
                proof.clear();
                proof.addAll(proof1);
                proof.addAll(proof2);
            }
            HashMap<String, Expression> map = new HashMap<>();
            map.put("1", v);
            map.put("2", notV);
            map.put("3", theorem);
            proof.add(AxiomScheme.SC_8.substitute(map));
            proof.add(new LogicalThen(new LogicalThen(notV, theorem), new LogicalThen(new LogicalOr(v, notV), theorem)));
            proof.add(new LogicalThen(new LogicalOr(v, notV), theorem));
            proof.add(theorem);
        }
        for (ArrayList<Expression> arr : tnd) {
            for (Expression e : arr) {
                out.println(e.asString());
            }
        }
        for (Expression expression : proof) {
            out.println(expression.asString());
        }

    }

    private ArrayList<Expression> tertiumNonDatur(Variable v) {
        LogicalNot notV = new LogicalNot(v);
        ArrayList<Expression> result = new ArrayList<>();
        try {
            result.add(parse("A->A|!A".replace("A", v.token)));

            for (InsaneHardcodedLemma4_4 s : InsaneHardcodedLemma4_4.values()) {
                result.add(s.replace(v, new LogicalOr(v, notV)));
            }
            result.add(parse("!(A|!A)->!A".replace("A", v.token)));

            result.add(parse("!A->A|!A".replace("A", v.token)));
            for (InsaneHardcodedLemma4_4 s : InsaneHardcodedLemma4_4.values()) {
                result.add(s.replace(new LogicalNot(v), new LogicalOr(v, notV)));
            }
            result.add(parse("!(A|!A)->!!A".replace("A", v.token)));


            result.add(parse("(!(A|!A)->!A)->(!(A|!A)->!!A)->(!!(A|!A))".replace("A", v.token)));
            result.add(parse("(!(A|!A)->!!A)->!!(A|!A)".replace("A", v.token)));
            result.add(parse("!!(A|!A)".replace("A", v.token)));
            result.add(parse("!!(A|!A)->(A|!A)".replace("A", v.token)));
            result.add(parse("A|!A".replace("A", v.token)));

        } catch (LexingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ParsingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }
}
