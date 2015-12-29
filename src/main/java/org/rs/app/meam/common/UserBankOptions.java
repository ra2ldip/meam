package org.rs.app.meam.common;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ra2ldip
 */
public class UserBankOptions {

    public static Map<Integer, String> accType = new HashMap<>();
    public static Map<Integer, String> crBydr = new HashMap<>();
    public static Map<Integer, String> creditGroup = new HashMap<>();
    public static Map<Integer, String> debitGroup = new HashMap<>();
    public static Map<Integer, String> bankType = new HashMap<>();

    /**
     * BANK TYPE
     */
    public static final int SAVINGS_AC = 1;
    public static final int FIXED_AC = 2;
    public static final int CURRENT_AC = 3;
    public static final int DEMAT_AC = 4;
    /**
     * CREDIT DEBIT TYPE
     */
    public static final int CREDIT = 5;
    public static final int DEBIT = 6;
    public static final int CREDIT_INTEREST = 7;
    public static final int DEBIT_FINE = 9;
    public static final int DEBIT_ATM = 10;
    public static final int DEBIT_NEFT = 11;
    public static final int DEBIT_ONLINE_PURCHASE = 12;
    public static final int CREDIT_SALARY = 13;
    public static final int CREDIT_ONLINE_TRANSFER = 14;
    public static final int CREDIT_REFUND = 15;
    /**
     * BANK CIRCLE
     */
    public static final int STATE_CIRCLE = 21;
    public static final int DOMESTIC_CIRCLE = 22;
    public static final int INTERNATIONAL_CIRCLE = 23;
    public static final int CO_OPERATIVE = 24;

    static {
        bankType.put(STATE_CIRCLE, "STATE CIRCLE");
        bankType.put(DOMESTIC_CIRCLE, "DOMESTIC CIRCLE");
        bankType.put(INTERNATIONAL_CIRCLE, "INTERNATIONAL CIRCLE");
        bankType.put(CO_OPERATIVE, "CO OPERATIVE");

        accType.put(SAVINGS_AC, "Savings Account");
        accType.put(FIXED_AC, "Fixed Account");
        accType.put(CURRENT_AC, "Current Account");
        accType.put(DEMAT_AC, "Dmat Account");

        crBydr.put(DEBIT, "Debited");
        crBydr.put(DEBIT_FINE, "Fine Debited");
        crBydr.put(DEBIT_ATM, "ATM Withdrwal");
        crBydr.put(DEBIT_NEFT, "NEFT Transfer");
        crBydr.put(DEBIT_ONLINE_PURCHASE, "Online Purchase");
        crBydr.put(CREDIT, "Credited");
        crBydr.put(CREDIT_INTEREST, "Interest Credit");
        crBydr.put(CREDIT_SALARY, "Salary Credited");
        crBydr.put(CREDIT_ONLINE_TRANSFER, "Online Payment Recieved");
        crBydr.put(CREDIT_REFUND, "Refunded");

        creditGroup.put(CREDIT, "Credited");
        creditGroup.put(CREDIT_INTEREST, "Interest Credit");
        creditGroup.put(CREDIT_SALARY, "Salary Credited");
        creditGroup.put(CREDIT_ONLINE_TRANSFER, "Online Payment Recieved");
        creditGroup.put(CREDIT_REFUND, "Refunded");
        
        debitGroup.put(DEBIT, "Debited");
        debitGroup.put(DEBIT_FINE, "Fine Debited");
        debitGroup.put(DEBIT_ATM, "ATM Withdrwal");
        debitGroup.put(DEBIT_NEFT, "NEFT Transfer");
        debitGroup.put(DEBIT_ONLINE_PURCHASE, "Online Purchase");

    }
}
