package ru.ifmo.ctddev.isaev;

import ru.ifmo.ctddev.isaev.hardcodedRules.AThenA;
import ru.ifmo.ctddev.isaev.parser.Lexer;
import ru.ifmo.ctddev.isaev.parser.LogicParser;
import ru.ifmo.ctddev.isaev.parser.Parser;
import ru.ifmo.ctddev.isaev.parser.PredicateParser;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.logic.Then;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Xottab
 * Date: 30.11.13
 */
public class General {
    public static BufferedReader in;
    public static PrintWriter out;
    protected static Lexer lexer = new Lexer();
    private static Parser parser = new LogicParser();
    private static int currentMode;

    public static void setParser(Parser parser) {
        General.parser = parser;
    }

    public static Expression parse(String s) {
        Expression expression = null;
        try {
            expression = parseExcept(s);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("string " + s + " looks kinda crappy, cant parse this");
            System.exit(1);
        }
        return expression;
    }

    public static Expression parseExcept(String s) throws Exception {
        Expression expression;
        /*if (parser instanceof ArithmeticParser) {
            String s1 = prefixPrimes(s).toString();
            s = s1;
        }*/
        String[] lexems = lexer.lex(s);
        expression = parser.parse(lexems);
        if (parser instanceof PredicateParser) {
            expression.setQuantifiers(new HashMap<>());
        }

        return expression;
    }
/*
    private static StringBuilder prefixPrimes(String s) throws ParsingException {
        StringBuilder sb = new StringBuilder(s);
        int i = sb.length() - 1;
        while (i >= 0) {
            if (sb.charAt(i) == '\'') {
                int primesCount = 0;
                while (sb.charAt(i) == '\'') {
                    primesCount++;
                    i--;
                }
                int pos = searchBackFindParentnessesPosition(sb, i);
                StringBuilder temp = prefixPrimes(sb.substring(pos, i + 1));
                i = pos - 1;
                StringBuilder temp2 = new StringBuilder(sb.substring(0, pos));
                for (int j = 0; j < primesCount; j++) {
                    temp2.append("'");
                }
                temp2.append(temp);
                temp2.append(sb.substring(pos + temp.length() + primesCount));
                sb = temp2;
            } else {
                i--;
            }
        }
        return sb;
    }

    private static int searchBackFindParentnessesPosition(StringBuilder sb, int i) throws ParsingException {
        int j = 0;
        int result = i;
        if (sb.charAt(result) == ')') {
            result--;
            if (result < 0) throw new ParsingException("Wrong parentnesses");
            while (!(j == 0 && sb.charAt(result) == '(')) {
                if (sb.charAt(result) == ')') j++;
                if (sb.charAt(result) == '(') j--;
                result--;
            }
        }
        return result;
    }*/

    public static List<Expression> proofAThenA(Expression alpha) {
        List<Expression> result = new ArrayList<>();
        for (AThenA e : AThenA.values()) {
            result.add(e.replace(alpha));
        }
        return result;
    }

    public static void addToMps(Map<Expression, ArrayList<Expression>> mps, Expression e) {
        if (e instanceof Then) {
            if (!mps.containsKey(((Then) e).getRight())) {
                mps.put(((Then) e).getRight(), new ArrayList<Expression>(3));
            }
            List<Expression> exprs = mps.get(((Then) e).getRight());
            exprs.add(((Then) e).getLeft());
        }
    }

    public static boolean isLowercaseVariable(String temp) {
        return Character.isLowerCase(temp.charAt(0)) && looksLikeSomething(temp);
    }

    private static boolean looksLikeSomething(String temp) {
        if (temp.length() > 1) {
            try {
                Integer.parseInt(temp.substring(1, temp.length()));
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    public static boolean isUppercaseVariable(String temp) {
        return Character.isUpperCase(temp.charAt(0)) && looksLikeSomething(temp);
    }

    public static boolean isVariable(String temp) {
        return Character.isLetter(temp.charAt(0)) && looksLikeSomething(temp);
    }

    public static void exitWithMessage(String s) {
        out.println(s);
        out.close();
        System.exit(1);
    }
}
