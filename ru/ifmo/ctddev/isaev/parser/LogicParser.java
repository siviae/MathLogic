package ru.ifmo.ctddev.isaev.parser;

import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.structure.*;
import ru.ifmo.ctddev.isaev.structure.logic.And;
import ru.ifmo.ctddev.isaev.structure.logic.Not;
import ru.ifmo.ctddev.isaev.structure.logic.Or;
import ru.ifmo.ctddev.isaev.structure.logic.Then;

import java.util.ArrayList;
import java.util.List;

import static ru.ifmo.ctddev.isaev.General.isLowercaseVariable;
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
        while (position < tokens.length && tokens[position].equals(Lexeme.THEN.token)) {
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

    private Expression disj() throws ParsingException {
        Expression result = conj();
        while (position < tokens.length && tokens[position].equals(Lexeme.OR.token)) {
            position++;
            result = new Or(result, conj());
        }
        return result;
    }

    private Expression conj() throws ParsingException {
        Expression result = unary();
        while (position < tokens.length && tokens[position].equals(Lexeme.AND.token)) {
            position++;
            result = new And(result, unary());
        }
        return result;
    }

    private Term term() throws ParsingException {
        boolean brackets = false;
        if (tokens[position].equals(Lexeme.LEFT_P.token)) {
            position++;
            brackets = true;
        }
        Term result;
        if (isLowercaseVariable(tokens[position])) {
            result = new Term(var().toString());
        } else
            throw new ParsingException("cannot parse term without name");

        if (tokens[position].equals(Lexeme.LEFT_P.token)) {
            position++;
            List<Term> arguments = new ArrayList<>(3);
            arguments.add(term());
            while (tokens[position].equals(Lexeme.COMMA.token)) {
                position++;
                arguments.add(term());
            }
            position++;
            result.arguments = arguments.toArray(new Term[arguments.size()]);
        }

        if (brackets) {
            position++;
        }
        return result;
    }

    private Predicate predicate() throws ParsingException {
        Predicate result;
        if (isUppercaseVariable(tokens[position])) {
            result = new Predicate(var().toString());
        } else
            throw new ParsingException("cannot predicate without name");

        if (tokens[position].equals(Lexeme.LEFT_P.token)) {
            position++;
            List<Term> arguments = new ArrayList<>(3);
            arguments.add(term());
            while (tokens[position].equals(Lexeme.COMMA.token)) {
                position++;
                arguments.add(term());
            }
            position++;
            result.arguments = arguments.toArray(new Term[arguments.size()]);
        }
        return result;
    }

    private Variable var() {
        Variable result = new Variable(tokens[position]);
        position++;
        return result;
    }

    private Expression unary() throws ParsingException {
        if (isUppercaseVariable(tokens[position])) {
            return var();
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
            return new Not(unary());
        }
        throw new ParsingException("unexpected symbol");
    }


}