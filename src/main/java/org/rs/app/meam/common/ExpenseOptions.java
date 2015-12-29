package org.rs.app.meam.common;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ra2ldip
 */
public class ExpenseOptions {

    public static Map<Integer, String> EXPENSE = new HashMap<Integer, String>();
    public static Map<Integer, String> EXP_TXN_TYPE = new HashMap<Integer, String>();

    public static final int TXN_CASH = 1;
    public static final int TXN_CARD = 2;
    public static final int TXN_CHECQUE = 3;
    public static final int TXN_NETBANKING = 4;

    public static final int WALLET_RECHARGE = 11;
    public static final int GROSSERY = 12;
    public static final int BREAK_FAST = 13;
    public static final int LUNCH = 14;
    public static final int SNACKS = 15;
    public static final int DINNER = 16;
    public static final int TRANSPORT = 17;
    public static final int OTHER = 18;
    public static final int ONLINE_PURCHASE = 19;
    public static final int BILL_PAYMENT = 20;
    public static final int RAIL_TKT_BOOKING = 21;
    public static final int FLIGHT_TKT_BOOKING = 22;
    public static final int BUS_TKT_BOOKING = 23;
    public static final int LOSE_MONEY = 24;
    public static final int MARKETING = 25;

    static {
        EXP_TXN_TYPE.put(TXN_CASH, "TXN CASH");
        EXP_TXN_TYPE.put(TXN_CARD, "TXN CARD");
        EXP_TXN_TYPE.put(TXN_CHECQUE, "TXN CHECQUE");
        EXP_TXN_TYPE.put(TXN_NETBANKING, "TXN NETBANKING");

        EXPENSE.put(WALLET_RECHARGE, "WALLET_RECHARGE");
        EXPENSE.put(GROSSERY, "GROSSERY");
        EXPENSE.put(BREAK_FAST, "BREAK_FAST");
        EXPENSE.put(LUNCH, "LUNCH");
        EXPENSE.put(SNACKS, "SNACKS");
        EXPENSE.put(DINNER, "DINNER");
        EXPENSE.put(TRANSPORT, "TRANSPORT");
        EXPENSE.put(OTHER, "OTHER");
        EXPENSE.put(ONLINE_PURCHASE, "ONLINE_PURCHASE");
        EXPENSE.put(BILL_PAYMENT, "BILL_PAYMENT");
        EXPENSE.put(RAIL_TKT_BOOKING, "RAIL_TKT_BOOKING");
        EXPENSE.put(FLIGHT_TKT_BOOKING, "FLIGHT_TKT_BOOKING");
        EXPENSE.put(BUS_TKT_BOOKING, "BUS_TKT_BOOKING");
        EXPENSE.put(LOSE_MONEY, "LOSE_MONEY");
        EXPENSE.put(MARKETING, "MARKETING");
    }
}
