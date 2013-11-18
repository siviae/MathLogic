package ru.ifmo.ctddev.isaev.parser;

import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.structure.*;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 05.11.13
 * Time: 19:20
 * To change this template use File | Settings | File Templates.
 */
public class Parser {

    private String[] tokens;
    private int position = 0;


    public Expression parse(String[] tokens) throws ParsingException {
        this.tokens = tokens;
        this.position = 0;
        return expr();
    }

    private Expression expr() throws ParsingException {
        Expression result = disj();
        ArrayList<Expression> arr = new ArrayList<>();
        arr.add(result);
        while (position < tokens.length && tokens[position].equals(Lexeme.THEN.token)) {
            position++;
            arr.add(disj());
        }
        if (arr.size() > 1) {
            result = arr.get(arr.size() - 1);
            int i = arr.size() - 2;
            while (i >= 0) {
                result = new LogicalThen(arr.get(i), result);
                i--;
            }
        }
        return result;
    }

    private Expression disj() throws ParsingException {
        Expression result = conj();
        while (position < tokens.length && tokens[position].equals(Lexeme.OR.token)) {
            position++;
            result = new LogicalOr(result, conj());
        }
        return result;
    }

    private Expression conj() throws ParsingException {
        Expression result = unary();
        while (position < tokens.length && tokens[position].equals(Lexeme.AND.token)) {
            position++;
            result = new LogicalAnd(result, unary());
        }
        return result;
    }

    private Expression unary() throws ParsingException {
        if (Lexer.isVariable(tokens[position])) {
            Expression result = new Variable(tokens[position]);
            position++;
            return result;
        }
        if (tokens[position].equals(Lexeme.LEFT_P.token)) {
            position++;

            Expression result = expr();
            if (!tokens[position].equals(Lexeme.RIGHT_P.token)) {
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

        if (tokens[position].equals(Lexeme.NOT.token)) {
            position++;
            return new LogicalNot(unary());
        }
        throw new ParsingException("unexpected symbol");
    }


}
