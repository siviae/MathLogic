import ru.ifmo.ctddev.isaev.Checker1;
import ru.ifmo.ctddev.isaev.Deduct2;
import ru.ifmo.ctddev.isaev.Homework;
import ru.ifmo.ctddev.isaev.exception.LexingException;
import ru.ifmo.ctddev.isaev.exception.ParsingException;
import ru.ifmo.ctddev.isaev.parser.Lexer;
import ru.ifmo.ctddev.isaev.parser.Parser;
import ru.ifmo.ctddev.isaev.structure.Expression;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 05.11.13
 * Time: 19:52
 * To change this template use File | Settings | File Templates.
 */
public class MathLogic {
    private static Homework work;

    public static void main(String[] args) {
        switch (args[0]) {
            case "1": {
                work = new Checker1();
                break;
            }
            case "2": {
                work = new Deduct2();
                break;
            }
            case "3": {
                break;
            }
            case "4": {
                break;
            }
            case "5": {
                break;
            }
            case "6": {
                break;
            }
            default:
                break;
        }
        try {
            work.in = new BufferedReader(new FileReader(args[1]));
            String[] arr = args[1].split("\\.");
            work.out = new PrintWriter(new FileWriter(arr[0] + ".out"));
            work.doSomething();
            work.in.close();
            work.out.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LexingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ParsingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}

