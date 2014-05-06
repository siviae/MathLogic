package ru.ifmo.ctddev.isaev.parser;

import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.arithmetics.*;
import ru.ifmo.ctddev.isaev.structure.logic.And;
import ru.ifmo.ctddev.isaev.structure.logic.Not;
import ru.ifmo.ctddev.isaev.structure.logic.Or;
import ru.ifmo.ctddev.isaev.structure.logic.Then;
import ru.ifmo.ctddev.isaev.structure.predicate.Exists;
import ru.ifmo.ctddev.isaev.structure.predicate.ForAll;
import ru.ifmo.ctddev.isaev.structure.predicate.Predicate;
import ru.ifmo.ctddev.isaev.structure.predicate.Term;

import java.util.ArrayList;

/**
 * User: Xottab
 * Date: 22.01.14
 */
public class ArithmeticParser extends PredicateParser {
    private static String expression;
    private static int index;

    @Override
    public Expression parse(String expression) throws ParsingException {
        ArithmeticParser.expression = expression.replaceAll("\\s+", "") + ";";
        index = 0;
        return implication();
    }

    private static char getChar() {
        return expression.charAt(index++);
    }

    private static void returnChar() {
        index--;
    }

    protected Expression implication() throws ParsingException {
        Expression s = Or();
        char nextChar = getChar();
        if (nextChar == '-') {
            if (getChar() != '>')
                throw new ParsingException("cannot parse: " + expression);
            s = new Then(s, implication());
        } else
            returnChar();
        return s;
    }

    protected Expression Or() throws ParsingException {
        Expression l = And();
        char nextChar = getChar();
        while (nextChar == '|') {
            l = new Or(l, And());
            nextChar = getChar();
        }
        returnChar();
        return l;
    }

    protected Expression And() throws ParsingException {
        Expression l = unary();
        char nextChar = getChar();
        while (nextChar == '&') {
            l = new And(l, unary());
            nextChar = getChar();
        }
        returnChar();
        return l;
    }

    protected Expression unary() throws ParsingException {
        char nextChar = getChar();
        if (Character.isLetter(nextChar) || Character.isDigit(nextChar)) {
            return predicate(Character.isUpperCase(nextChar));
        } else if (nextChar == '!') {
            return new Not(unary());
        } else if (nextChar == '(') {
            int saveIndex = index;
            Expression result;
            try {
                result = implication();
                if (getChar() != ')')
                    throw new ParsingException("cannot parse: " + expression);
            } catch (ParsingException pe) {
                index = saveIndex;
                result = predicate(false);
            }
            return result;
        } else if (nextChar == '@') {
            {

                return new ForAll(variable(), unary());
            }
        } else if (nextChar == '?') {
            return new Exists(variable(), unary());
        } else {
            throw new ParsingException("cannot parse: " + expression);
        }
    }

    protected Expression predicate(boolean firstCharIsUpperCase) throws ParsingException {
        if (firstCharIsUpperCase) {
            int start = index - 1, end = index;
            char nextChar;
            while (Character.isDigit(nextChar = getChar()))
                end++;
            String name = expression.substring(start, end);
            ArrayList<Term> list = null;
            if (nextChar == '(') {
                list = terms();
                if (getChar() != ')')
                    throw new ParsingException("cannot parse: " + expression);
            } else
                returnChar();
            return new Predicate(name, list);
        } else {
            returnChar();
            ArrayList<Term> list = new ArrayList<Term>();
            list.add(term());
            if (getChar() != '=')
                throw new ParsingException("cannot parse: " + expression);
            list.add(term());
            return new Equals(list);
        }
    }

    protected Term term() throws ParsingException {
        Term t = summand();
        char nextChar = getChar();
        while (nextChar == '+') {
            ArrayList<Term> list = new ArrayList<Term>();
            list.add(t);
            list.add(summand());
            t = new Plus(list);
            nextChar = getChar();
        }
        returnChar();
        return t;
    }

    protected Term summand() throws ParsingException {
        Term t = multiplied();
        char nextChar = getChar();
        while (nextChar == '*') {
            ArrayList<Term> list = new ArrayList<Term>();
            list.add(t);
            list.add(multiplied());
            t = new Mul(list);
            nextChar = getChar();
        }
        returnChar();
        return t;
    }

    protected Term multiplied() throws ParsingException {
        char nextChar = getChar();
        Term t;
        if (nextChar == '(') {
            t = term();
            if (getChar() != ')')
                throw new ParsingException("cannot parse: " + expression);
        } else {
            returnChar();
            t = variableOrNull();
            if (!"0".equals(t.name)) {
                nextChar = getChar();
                if (nextChar == '(') {
                    ArrayList<Term> list = terms();
                    if (getChar() != ')')
                        throw new ParsingException("cannot parse: " + expression);

                    switch (t.name) {
                        case "+": {
                            t = new Plus(list);
                            break;
                        }
                        case "*": {
                            t = new Mul(list);
                            break;
                        }
                        case "'": {
                            t = new Prime(list);
                            break;
                        }
                        default: {
                            throw new ParsingException("cannot parse: " + expression);
                        }
                    }
                    // t = new Function(t.name, list);
                } else {
                    returnChar();
                }
            }
        }
        if (getChar() == '\'') {
            do {
                ArrayList<Term> list = new ArrayList<Term>();
                list.add(t);
                t = new Prime(list);
            }
            while (getChar() == '\'');
            returnChar();
        } else
            returnChar();
        return t;
    }

    private static Term variableOrNull() throws ParsingException {
        if (getChar() == '0') {
            return new Zero();
        } else {
            returnChar();
            return variable();
        }
    }

    private static Term variable() throws ParsingException {
        int start = index, end = index + 1;
        char nextChar = getChar();
        if (!Character.isLetter(nextChar) || !Character.isLowerCase(nextChar))
            throw new ParsingException("cannot parse: " + expression);
        while (Character.isDigit(getChar()))
            end++;
        returnChar();
        String value = expression.substring(start, end);
        return new Term(value);
    }

    protected ArrayList<Term> terms() throws ParsingException {
        ArrayList<Term> list = new ArrayList<Term>();
        list.add(term());
        while (getChar() == ',')
            list.add(term());
        returnChar();
        return list;
    }
}
