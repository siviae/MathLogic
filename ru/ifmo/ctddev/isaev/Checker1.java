package ru.ifmo.ctddev.isaev;

import ru.ifmo.ctddev.isaev.exception.LexingException;
import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.parser.Lexer;
import ru.ifmo.ctddev.isaev.parser.Parser;
import ru.ifmo.ctddev.isaev.structure.AxiomScheme;
import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.LogicalThen;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 10.11.13
 * Time: 13:21
 * To change this template use File | Settings | File Templates.
 */
public class Checker1 extends Homework {


    @Override
    public void doSomething() throws IOException {
        try {
            List<Expression> proofed = new ArrayList<>();
            String temp = in.readLine();
            row = 1;
            while (temp != null) {
                boolean f = false;
                Expression expr = parse(temp);
                for (AxiomScheme scheme : AxiomScheme.values()) {
                    f = scheme.match(expr);
                    if (f) {
                        break;
                    }
                }
                if (!f) {
                    for (int i = 0; i < proofed.size(); i++) {
                        f = proofed.get(i).match(expr);
                        for (Expression aProofed : proofed) {
                            f = f || modusPonens(proofed.get(i), aProofed, expr);
                        }
                        if (f) break;
                    }
                }
                if (!f) {
                    out.println("Доказательство некорректно начиная с " + row + " высказывания.");
                    out.close();
                    System.exit(0);
                }
                proofed.add(expr);
                row++;
                temp = in.readLine();

            }
            out.println("Доказательство корректно.");
            out.close();
            System.exit(0);

        } catch (LexingException e) {
            out.println("error occured while dividing a row " + row + " into lexems: " + e.getMessage());
        } catch (ParsingException e) {
            out.println("error occured while parsing row " + row + ": " + e.getMessage());
        }

    }
}
