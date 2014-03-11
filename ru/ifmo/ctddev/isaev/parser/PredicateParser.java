package ru.ifmo.ctddev.isaev.parser;

import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.Predicate;
import ru.ifmo.ctddev.isaev.structure.Term;
import ru.ifmo.ctddev.isaev.structure.Variable;
import ru.ifmo.ctddev.isaev.structure.logic.Not;
import ru.ifmo.ctddev.isaev.structure.logic.Then;
import ru.ifmo.ctddev.isaev.structure.predicate.Exists;
import ru.ifmo.ctddev.isaev.structure.predicate.ForAll;

import java.util.ArrayList;
import java.util.List;

import static ru.ifmo.ctddev.isaev.General.isLowercaseVariable;
import static ru.ifmo.ctddev.isaev.General.isUppercaseVariable;

/**
 * User: Xottab
 * Date: 22.01.14
 */
public class PredicateParser extends LogicParser {
    /**
     * Инвариант для каждого метода - после окончания парсинга, поциция следующего токена должна быть перенесена
     */
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

   /* protected Expression disj() throws ParsingException {
        return  super.disj();
    }

    protected Expression conj() throws ParsingException {
        return super.conj();
    }*/

    @Override
    protected Expression unary() throws ParsingException {
        Expression result;
        if (tokens[position].equals(Lexeme.NOT.s)) {
            position++;
            result = new Not(unary());
            return result;
        }
        if (tokens[position].equals(Lexeme.FOR_ALL.s)) {
            position++;
            Variable var = var();
            result = new ForAll(var, unary());
            return result;
        }
        if (tokens[position].equals(Lexeme.EXISTS.s)) {
            position++;
            Variable var = var();
            result = new Exists(var, unary());
            return result;
        }
        if (tokens[position].equals(Lexeme.LEFT_P.s)) {
            position++;
            result = expr();
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

        if (isUppercaseVariable(tokens[position])) {
            return predicate();
        }
        throw new ParsingException("unexpected symbol");
    }

    /*protected Variable var() {
        Variable result = new Variable(tokens[position]);
        position++;
        return result;
    }*/

    protected Predicate predicate() throws ParsingException {
        Predicate result;
        result = new Predicate(tokens[position]);
        position++;
        if (tokens[position].equals(Lexeme.LEFT_P.s)) {
            position++;
            List<Term> arguments = new ArrayList<>(3);
            arguments.add(term());
            while (tokens[position].equals(Lexeme.COMMA.s)) {
                position++;
                arguments.add(term());
            }
            position++;
            result.setArguments(arguments.toArray(new Term[arguments.size()]));
        } /*else {
            position++;
        }*/
        return result;
    }

    protected Term term() throws ParsingException {
        Term result;
        if (tokens[position].equals(Lexeme.LEFT_P.s)) {
            position++;
            result = term();
        } else if (isLowercaseVariable(tokens[position])) {
            result = new Term(tokens[position]);
            if (tokens[position].equals(Lexeme.LEFT_P.s)) {
                position++;
                List<Term> arguments = new ArrayList<>(3);
                arguments.add(term());
                while (tokens[position].equals(Lexeme.COMMA.s)) {
                    position++;
                    arguments.add(term());
                }
                result.setArguments(arguments.toArray(new Term[arguments.size()]));
            } else {
                result = new Variable(result.getName());
            }
        } else
            throw new ParsingException("cannot parse term without name, incorrect invocation");

        position++;
        return result;
    }

}
