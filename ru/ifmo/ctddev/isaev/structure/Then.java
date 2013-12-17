package ru.ifmo.ctddev.isaev.structure;

import ru.ifmo.ctddev.isaev.helpers.InsaneHardcodedContrapositionRule;
import ru.ifmo.ctddev.isaev.parser.Lexeme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ru.ifmo.ctddev.isaev.General.parse;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 05.11.13
 * Time: 20:32
 * To change this template use File | Settings | File Templates.
 */
public class Then extends Binary {
    public Then(Expression left, Expression right) {
        super(left, right);
        this.token = Lexeme.THEN;
    }

    @Override
    public boolean match(Expression other) {
        return other instanceof Then
                && ((Then) other).left.match(left)
                && ((Then) other).right.match(right);
    }

    @Override
    public boolean hasSameType(Expression other) {
        return other instanceof Then;
    }

    @Override
    public boolean evaluate() {
        return !left.evaluate() || right.evaluate();
    }

    @Override
    public List<Expression> getParticularProof(ArrayList<Expression> hypos) {
        List<Expression> result = new ArrayList<>();
        Expression currP = null;
        Expression currQ = null;
        String[] proof = null;
        if (!hypos.contains(left)) {
            result.addAll(left.getParticularProof(hypos));
        }
        if (!hypos.contains(right)) {
            result.addAll(right.getParticularProof(hypos));
        }
        if (this.match(new Then(new NumExpression(1), new NumExpression(2)))) {
            currP = left;
            currQ = right;
            proof = new String[]{
                    "Q->P->Q",
                    "P->Q"
            };
        } else if (this.match(new Then(new Not(new NumExpression(1)), new NumExpression(2)))) {
            currP = ((Not) left).operand;
            currQ = right;
            proof = new String[]{
                    "Q->P->Q",
                    "P->Q"
            };
        } else if (this.match(new Then(new NumExpression(1), new Not(new NumExpression(2))))) {
            currP = left;
            currQ = ((Not) right).operand;
            List<String> proof1 = new ArrayList<>();
            proof1.add("P->((P->Q)->P))");
            proof1.add("((P->Q)->P)");
            proof1.add("((P->Q)->((P->Q)->(P->Q)))");
            proof1.add("((P->Q)->(((P->Q)->(P->Q))->(P->Q)))");
            proof1.add("(((P->Q)->((P->Q)->(P->Q)))->(((P->Q)->(((P->Q)->(P->Q))->(P->Q)))->((P->Q)->(P->Q))))");
            proof1.add("(((P->Q)->(((P->Q)->(P->Q))->(P->Q)))->((P->Q)->(P->Q)))");
            proof1.add("((P->Q)->(P->Q))");
            proof1.add("(((P->Q)->P)->(((P->Q)->(P->Q))->((P->Q)->Q)))");
            proof1.add("(((P->Q)->(P->Q))->((P->Q)->Q))");
            proof1.add("((P->Q)->Q)");
            for (InsaneHardcodedContrapositionRule rule : InsaneHardcodedContrapositionRule.values()) {
                proof1.add(rule.replace("P->Q", "Q"));
            }
            proof1.add("!Q->!(P->Q))");
            proof1.add("!(P->Q))");
        } else if (this.match(new Then(new Not(new NumExpression(1)), new Not(new NumExpression(2))))) {
            currP = ((Not) left).operand;
            currQ = ((Not) right).operand;
            List<String> proof1 = new ArrayList<>();

            proof1.add("P->((P->Q)->P))");
            proof1.add("((P->Q)->P)");
            proof1.add("((P->Q)->((P->Q)->(P->Q)))");
            proof1.add("((P->Q)->(((P->Q)->(P->Q))->(P->Q)))");
            proof1.add("(((P->Q)->((P->Q)->(P->Q)))->(((P->Q)->(((P->Q)->(P->Q))->(P->Q)))->((P->Q)->(P->Q))))");
            proof1.add("(((P->Q)->(((P->Q)->(P->Q))->(P->Q)))->((P->Q)->(P->Q)))");
            proof1.add("((P->Q)->(P->Q))");
            proof1.add("(((P->Q)->P)->(((P->Q)->(P->Q))->((P->Q)->Q)))");
            proof1.add("(((P->Q)->(P->Q))->((P->Q)->Q))");
            proof1.add("((P->Q)->Q)");
            for (InsaneHardcodedContrapositionRule rule : InsaneHardcodedContrapositionRule.values()) {
                proof1.add(rule.replace("P->Q", "Q"));
            }
            proof1.add("!Q->!(P->Q))");
            proof1.add("!(P->Q))");
        }
        if (!hypos.contains(left)) {
            result.addAll(currP.getParticularProof(hypos));
        }
        if (!hypos.contains(right)) {
            result.addAll(currQ.getParticularProof(hypos));
        }
        for (String s : proof) {
            result.add(parse(s.replace("Q", currQ.toString()).replace("P", currP.toString())));
        }
        return result;
    }

    @Override
    public Expression substitute(HashMap<String, Expression> variables) {
        return new Then(left.substitute(variables), right.substitute(variables));
    }
}
