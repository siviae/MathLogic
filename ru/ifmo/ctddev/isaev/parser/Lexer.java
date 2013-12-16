package ru.ifmo.ctddev.isaev.parser;

import ru.ifmo.ctddev.isaev.exception.LexingException;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 05.11.13
 * Time: 19:20
 * To change this template use File | Settings | File Templates.
 */
public class Lexer {
    private String target;

    public String[] lex(String t) throws LexingException {
        this.target = t.replaceAll("\\s+", "");
        ArrayList<String> result = new ArrayList<String>();
        int l = 0;
        int r = 0;
        boolean f;
        while (r < target.length()) {
            ++r;
            f = true;
            String temp = target.substring(l, r);
            for (Lexeme lex : Lexeme.values()) {
                if (temp.equals(lex.token)) {
                    result.add(temp);
                    l = r;
                    f = false;
                }
            }
            if (f && isVariable(target.substring(l, r))) {
                if (r + 1 < target.length() && isVariable(target.substring(l, r + 1))) {
                    ++r;
                    result.add(target.substring(l, r));
                    l = r;
                } else {
                    result.add(temp);
                    l = r;
                }

            }
        }
        if (l - r > 0) throw new LexingException();
        return result.toArray(new String[result.size()]);
    }

    public static boolean isVariable(String temp) {
        boolean f = true;
        if (temp.length() > 2) f = false;
        if (Character.getType(temp.charAt(0)) != Character.UPPERCASE_LETTER) f = false;
        if (temp.length() == 2 && !Character.isDigit(temp.charAt(1))) f = false;
        return f;
    }

}
