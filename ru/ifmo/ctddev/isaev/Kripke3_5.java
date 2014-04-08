package ru.ifmo.ctddev.isaev;

import ru.ifmo.ctddev.isaev.exception.IncorrectProofException;
import ru.ifmo.ctddev.isaev.exception.LexingException;
import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.exception.ProofGeneratingException;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.logic.Variable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.ifmo.ctddev.isaev.General.*;

/**
 * User: Xottab
 * Date: 06.04.14
 */
public class Kripke3_5 extends Homework {
    @Override
    public void doSomething() throws IOException, ParsingException, LexingException, IncorrectProofException, ProofGeneratingException {
        Expression theorem = parse(in.readLine());
        Map<String, Variable> vars = theorem.getVars();
        int n = (int) Math.pow(2, vars.size());
        List<Variable> variables = new ArrayList<>(vars.values());
        for (int i = 0; i < n; i++) {
            int k = i;
            for (Expression v : vars.values()) {
                ((Variable) v).setCurrentValue(k % 2 == 1);
                k /= 2;
            }
            theorem = theorem.substituteAndCopy(vars);
            boolean f = theorem.evaluate();
            if (!f) {
                StringBuilder sb = new StringBuilder("Expression is false when ");
                for (int j = 0; j < variables.size(); j++) {
                    sb.append(variables.get(j).getName()).append("=").append(variables.get(j).getCurrentValue() ? "True" : "False");
                    if (j != variables.size() - 1) {
                        sb.append(", ");
                    }
                }
                out.println(sb);
                out.close();
                System.exit(0);
            }
            out.println("Высказывание общезначимо в интуиционистской логике");
        }
    }
}
