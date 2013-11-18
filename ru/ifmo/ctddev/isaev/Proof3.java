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
        proof.add(theorem);
       while(variables.size()>0){
           Variable v = (Variable) variables.remove(variables.size()-1);
           deduct = new Deduct2(variables,v);
           List<Expression> proof1 = deduct.move1HypoToProof(proof);
           deduct.setAlpha(new LogicalNot(v));
           List<Expression> proof2 = deduct.move1HypoToProof(proof);


       }


    }
}
