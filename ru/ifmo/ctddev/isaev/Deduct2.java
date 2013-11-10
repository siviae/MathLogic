package ru.ifmo.ctddev.isaev;

import ru.ifmo.ctddev.isaev.exception.LexingException;
import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.structure.AxiomScheme;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.LogicalThen;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 10.11.13
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
public class Deduct2 extends Homework {


    @Override
    public void doSomething() throws IOException, ParsingException, LexingException {
        List<Expression> hypos = new ArrayList<>();
        List<Expression> proofed = new ArrayList<>();
        String[] temp = in.readLine().split(Pattern.quote("|-"));
        if (temp.length != 2) {
            throw new IOException("more than one |- in first line");
        }
        Expression alpha = null;
        String[] s = temp[0].split(",");
        for (int i = 0; i < s.length; i++) {
            if (i == s.length - 1) {
                alpha = parse(s[i]);
            } else {
                hypos.add(parse(s[i]));
            }
        }

        String s1 = in.readLine();
        while (s1 != null) {
            Expression expr = parse(s1);
            boolean f = false;
            for (Expression e : hypos) {
                if (e.match(expr)) {
                    f = true;
                    break;
                }
            }
            if (!f) {
                for (AxiomScheme scheme : AxiomScheme.values()) {
                    if (scheme.match(expr)) {
                        f = true;
                        break;
                    }
                }
            }
            if (f) {
                out.println(expr.asString());
                out.println(new LogicalThen(expr, new LogicalThen(alpha, expr)).asString());
                out.println(new LogicalThen(alpha, expr).asString());
            }
            if (!f && expr.match(alpha)) {
                out.println("*->(*->*)".replace("*", alpha.asString()));
                out.println("*->(*->*)->*".replace("*", alpha.asString()));
                out.println("(*->(*->*))->((*->((*->*)->*))->(*->*))".replace("*", alpha.asString()));
                out.println("((*->((*->*)->*))->(*->*))".replace("*", alpha.asString()));
                out.println("*->*".replace("*", alpha.asString()));
                f = true;
            }
            if (!f) {
                for (int i = 0; i < proofed.size(); i++) {
                    for (int j = 0; j < proofed.size(); j++) {
                        if (modusPonens(proofed.get(i), proofed.get(j), expr)) {
                            out.println("(1->2)->((1->(2->3))->(1->3))".replace("1", alpha.asString()).replace("2", proofed.get(j).asString().toString()).replace("3", proofed.get(i).asString()));
                            out.println("((1->(2->3))->(1->3))".replace("1", alpha.asString()).replace("2", proofed.get(j).asString().toString()).replace("3", proofed.get(i).asString()));
                            out.println(new LogicalThen(alpha, expr).asString());
                        }
                    }
                }
            }
            proofed.add(expr);
            s1 = in.readLine();
        }


    }
}
