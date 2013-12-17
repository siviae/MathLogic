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
                while (r < target.length() & isVariable(target.substring(l, r))) {
                    ++r;
                }
                temp = target.substring(l, --r);
                result.add(temp);
                l = r;
            }
        }
        if (l - r > 0) throw new LexingException();
        return result.toArray(new String[result.size()]);
    }

    public static boolean isVariable(String temp) {
        if (Character.getType(temp.charAt(0)) != Character.UPPERCASE_LETTER) return false;
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

}
