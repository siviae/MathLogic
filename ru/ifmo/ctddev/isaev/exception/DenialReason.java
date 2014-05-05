package ru.ifmo.ctddev.isaev.exception;

import static ru.ifmo.ctddev.isaev.General.out;

/**
 * User: Xottab
 * Date: 29.04.14
 */

public enum DenialReason {
    ERROR_1("терм %s не свободен для подстановки в формулу %s вместо переменной %s."),
    ERROR_2("переменная %s входит свободно в формулу %s."),
    ERROR_3("используется %s с квантором по переменной %s, " +
            "входящей свободно в допущение %s.");
    String reason;

    DenialReason(String reason) {
        this.reason = reason;
    }

    public void create(int row, String... params) {
        out.println("Вывод некорректен, начиная с формулы № " + row + "\n");
        reason = String.format(reason, params);
        out.println(reason);
        out.close();
        System.exit(0);
    }

}