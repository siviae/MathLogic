package ru.ifmo.ctddev.isaev.hardcodedRules;

import ru.ifmo.ctddev.isaev.structure.Expression;
import ru.ifmo.ctddev.isaev.structure.logic.Not;
import ru.ifmo.ctddev.isaev.structure.logic.NumExpr;
import ru.ifmo.ctddev.isaev.structure.logic.Then;

import java.util.HashMap;
import java.util.Map;

import static ru.ifmo.ctddev.isaev.General.parse;

/**
 * Created with IntelliJ IDEA.
 * User: Xottab
 * Date: 18.11.13
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
public enum ContrapositionRule {

    R_1(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))),
    R_2(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))))),
    R_3(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))))),
    R_4(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))))),
    R_5(new Then(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))))))),
    R_6(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))))))),
    R_7(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))), new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))))))),
    R_8(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))))))),
    R_9(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))))),
    R_10(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2))))),
    R_11(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2))), new Then(new NumExpr(1), new NumExpr(2))))),
    R_12(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2)))), new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2))), new Then(new NumExpr(1), new NumExpr(2)))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2)))))),
    R_13(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2))), new Then(new NumExpr(1), new NumExpr(2)))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2))))),
    R_14(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2)))),
    R_15(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2))))),
    R_16(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2)))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2))))))),
    R_17(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2)))))),
    R_18(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2))), new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2))))))),
    R_19(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2)))))),
    R_20(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2))))),
    R_21(new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))))),
    R_22(new Then(new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))))))),
    R_23(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))))))),
    R_24(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2)))), new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))))))),
    R_25(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new NumExpr(2))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))))))),
    R_26(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))))),
    R_27(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))))), new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))))))),
    R_28(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))))),
    R_29(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))))),
    R_30(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))),
    R_31(new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))))),
    R_32(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))))),
    R_33(new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))), new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))))),
    R_34(new Then(new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))), new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))), new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))))))),
    R_35(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))), new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))))))),
    R_36(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))), new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))), new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))))))),
    R_37(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))), new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))))))),
    R_38(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))))),
    R_39(new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))))),
    R_40(new Then(new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2)))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))))))),
    R_41(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2)))))),
    R_42(new Then(new Not(new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Not(new NumExpr(2))))),
    R_43(new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Not(new NumExpr(2)))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Not(new NumExpr(2))))))),
    R_44(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Not(new NumExpr(2)))))),
    R_45(new Then(new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2)))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Not(new NumExpr(2)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2)))))),
    R_46(new Then(new Then(new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2)))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Not(new NumExpr(2)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2)))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Not(new NumExpr(2)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2)))))))),
    R_47(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2)))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Not(new NumExpr(2)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))))))),
    R_48(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))))), new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2)))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Not(new NumExpr(2)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2)))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Not(new NumExpr(2)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2)))))))),
    R_49(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2)))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Not(new NumExpr(2)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2)))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Not(new NumExpr(2)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))))))),
    R_50(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Not(new NumExpr(2)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2)))))),
    R_51(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Not(new NumExpr(2))))), new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Not(new NumExpr(2)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))))))),
    R_52(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Not(new NumExpr(2)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2)))))),
    R_53(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))))),
    R_54(new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))))),
    R_55(new Then(new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))))))),
    R_56(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))))))),
    R_57(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2)))), new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))))))),
    R_58(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Not(new NumExpr(2))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))))))),
    R_59(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))))),
    R_60(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))),
    R_61(new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))))),
    R_62(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))))),
    R_63(new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(1)))))),
    R_64(new Then(new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(1))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(1)))))))),
    R_65(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(1))))))),
    R_66(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))), new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(1)))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(1)))))))),
    R_67(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(1)))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(1))))))),
    R_68(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(1)))))),
    R_69(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2))))), new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(1)))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(1)))))))),
    R_70(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new NumExpr(1), new Not(new NumExpr(2)))), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(1)))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(1))))))),
    R_71(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(1)))))),
    R_72(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1))))), new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(1))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(1))))))),
    R_73(new Then(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Then(new Not(new NumExpr(2)), new Then(new Then(new NumExpr(1), new Not(new NumExpr(2))), new Not(new NumExpr(1)))), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(1))))), new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(1)))))),
    R_74(new Then(new Then(new NumExpr(1), new NumExpr(2)), new Then(new Not(new NumExpr(2)), new Not(new NumExpr(1)))));
    private Expression expression;


    ContrapositionRule(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public Expression replace(Expression e, Expression e1) {
        Map<String, Expression> map = new HashMap<>();
        map.put("1", e);
        map.put("2", e1);
        return expression.substituteAndCopy(map);
    }

    public String replace(String e, String e1) {
        return replace(parse(e), parse(e1)).toString();
    }
}
