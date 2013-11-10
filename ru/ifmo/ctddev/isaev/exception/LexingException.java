package ru.ifmo.ctddev.isaev.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 05.11.13
 * Time: 19:34
 * To change this template use File | Settings | File Templates.
 */
public class LexingException extends Exception {
    public LexingException() {
        super("unknown lexem");
    }
}
