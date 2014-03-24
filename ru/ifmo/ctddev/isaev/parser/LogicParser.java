package ru.ifmo.ctddev.isaev.parser;

import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.logic.*;

import java.util.ArrayList;

import static ru.ifmo.ctddev.isaev.General.isUppercaseVariable;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 05.11.13
 * Time: 19:20
 * To change this template use File | Settings | File Templates.
 */
public class LogicParser extends Parser {

    @Override
    protected Expression expr() throws ParsingException {
        Expression result = disj();
        ArrayList<Expression> arr = new ArrayList<>();
        arr.add(result);
        while (position < tokens.length && tokens[position].equals(Lexeme.THEN.s)) {
            position++;
            arr.add(disj());
        }
        if (arr.size() > 1) {
            result = arr.get(arr.size() - 1);
            int i = arr.size() - 2;
            while (i >= 0) {
                result = new Then(arr.get(i), result);
                i--;
            }
        }
        return result;
    }

    protected Expression disj() throws ParsingException {
        Expression result = conj();
        while (position < tokens.length && tokens[position].equals(Lexeme.OR.s)) {
            position++;
            result = new Or(result, conj());
        }
        return result;
    }

    protected Expression conj() throws ParsingException {
        Expression result = unary();
        while (position < tokens.length && tokens[position].equals(Lexeme.AND.s)) {
            position++;
            result = new And(result, unary());
        }
        return result;
    }

    protected Variable var() {
        Variable result = new Variable(tokens[position]);
        position++;
        return result;
    }

    protected Expression unary() throws ParsingException {
        if (isUppercaseVariable(tokens[position])) {
            return var();
        }
        if (tokens[position].equals(Lexeme.LEFT_P.s)) {
            position++;

            Expression result = expr();
            if (!tokens[position].equals(Lexeme.RIGHT_P.s)) {
                StringBuilder sb = new StringBuilder();
                for (String s : tokens) {
                    sb.append(s);
                }
                throw new ParsingException("you have unclosed brackets in expression " + sb.toString());
            } else {
                position++;
            }
            return result;
        }

        if (tokens[position].equals(Lexeme.NOT.s)) {
            position++;
            return new Not(unary());
        }
        throw new ParsingException("unexpected symbol");
    }


}
