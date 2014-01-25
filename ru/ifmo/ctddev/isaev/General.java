package ru.ifmo.ctddev.isaev;

import ru.ifmo.ctddev.isaev.helpers.AThenA;
import ru.ifmo.ctddev.isaev.parser.Lexer;
import ru.ifmo.ctddev.isaev.parser.LogicParser;
import ru.ifmo.ctddev.isaev.parser.Parser;
import ru.ifmo.ctddev.isaev.structure.Expression;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
            String[] lexems = lexer.lex(s);
            expression = parser.parse(lexems);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("string " + s + " looks kinda crappy, cant parse this");
            System.exit(1);
        }
        return expression;
    }



    public static List<Expression> proofAThenA(Expression alpha) {
        List<Expression> result = new ArrayList<>();
        for (AThenA e: AThenA.values()) {
            result.add(e.replace(alpha));
        }
        return result;
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
}
