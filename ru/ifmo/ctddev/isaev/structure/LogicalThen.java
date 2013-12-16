package ru.ifmo.ctddev.isaev.structure;

import ru.ifmo.ctddev.isaev.helpers.AxiomScheme;
import ru.ifmo.ctddev.isaev.helpers.InsaneHardcodedContrapositionRule;
import ru.ifmo.ctddev.isaev.parser.Lexeme;

import static ru.ifmo.ctddev.isaev.General.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 05.11.13
 * Time: 20:32
 * To change this template use File | Settings | File Templates.
 */
public class LogicalThen extends LogicalBinary {
    public LogicalThen(Expression left, Expression right) {
        super(left, right);
        this.token = Lexeme.THEN;
    }

    @Override
    public boolean match(Expression other) {
        return other instanceof LogicalThen
                && ((LogicalThen) other).left.match(left)
                && ((LogicalThen) other).right.match(right);
    }

    @Override
    public boolean hasSameType(Expression other) {
        return other instanceof LogicalThen;
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
        if (this.match(new LogicalThen(new NumExpression(1), new NumExpression(2)))) {
            currP = left;
            currQ = right;
            proof = new String[]{
                    "Q->P->Q",
                    "P->Q"
            };
        } else if (this.match(new LogicalThen(new LogicalNot(new NumExpression(1)), new NumExpression(2)))) {
            currP = ((LogicalNot) left).operand;
            currQ = right;
            proof = new String[]{
                    "Q->P->Q",
                    "P->Q"
            };
        } else if (this.match(new LogicalThen(new NumExpression(1), new LogicalNot(new NumExpression(2))))) {
            currP = left;
            currQ = ((LogicalNot) right).operand;
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
        } else if (this.match(new LogicalThen(new LogicalNot(new NumExpression(1)), new LogicalNot(new NumExpression(2))))) {
            currP = ((LogicalNot) left).operand;
            currQ = ((LogicalNot) right).operand;
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
        return new LogicalThen(left.substitute(variables), right.substitute(variables));
    }
}
