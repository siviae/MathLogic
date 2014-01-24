package ru.ifmo.ctddev.isaev.parser;

import ru.ifmo.ctddev.isaev.exception.LexingException;

import java.util.ArrayList;

import static ru.ifmo.ctddev.isaev.General.isUppercaseVariable;

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
            if (f && isUppercaseVariable(temp)) {
                while (r < target.length() && isUppercaseVariable(target.substring(l, r + 1))) {
                    ++r;
                }
                temp = target.substring(l, r);
                result.add(temp);
                l = r;
            }
        }
        if (l - r > 0) throw new LexingException();
        return result.toArray(new String[result.size()]);
    }

}
