package ru.ifmo.ctddev.isaev;

import ru.ifmo.ctddev.isaev.exception.LexingException;
import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.parser.Lexer;
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
    protected static Parser parser = new Parser();

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

    public static List<String> proofAThenA(String alpha) {
        List<String> result = new ArrayList<>();
        result.add("*->(*->*)".replace("*", alpha));
        result.add("*->(*->*)->*".replace("*", alpha));
        result.add("(*->(*->*))->((*->((*->*)->*))->(*->*))".replace("*", alpha));
        result.add("((*->((*->*)->*))->(*->*))".replace("*", alpha));
        result.add("*->*".replace("*", alpha));
        return result;
    }

    public static List<Expression> proofAThenA(Expression alpha) {
        List<Expression> result = new ArrayList<>();
        List<String> strings = proofAThenA(alpha.toString());
        for (String s : strings) {
            result.add(parse(s));
        }
        return result;
    }
}
