package org.rs.app.meam.expense;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author ra2ldip
 */
@Entity
public class DailyExpense implements Serializable {

    public static int ACTIVE = 0;
    public static int DELETED = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    private long userId;
    private Date date;
    private int txnType;//ONLE?CASH?CHECQUE?NETBANKING
    private int expType;
    private BigDecimal walletBalance;
    private BigDecimal expnse;
    private String expDetails;
    private int activeState = ACTIVE;

    public DailyExpense() {
    }

    public DailyExpense(long userId, Date date, int txnType, int expType, BigDecimal expnse, String expDetails) {
        this.userId = userId;
        this.date = date;
        this.txnType = txnType;
        this.expType = expType;
        this.expnse = expnse;
        this.expDetails = expDetails;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTxnType() {
        return txnType;
    }

    public void setTxnType(int txnType) {
        this.txnType = txnType;
    }

    public int getExpType() {
        return expType;
    }

    public void setExpType(int expType) {
        this.expType = expType;
    }

    public BigDecimal getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(BigDecimal walletBalance) {
        this.walletBalance = walletBalance;
    }

    public BigDecimal getExpnse() {
        return expnse;
    }

    public void setExpnse(BigDecimal expnse) {
        this.expnse = expnse;
    }

    public String getExpDetails() {
        return expDetails;
    }

    public void setExpDetails(String expDetails) {
        this.expDetails = expDetails;
    }

    public int getActiveState() {
        return activeState;
    }

    public void setActiveState(int activeState) {
        this.activeState = activeState;
    }
}
