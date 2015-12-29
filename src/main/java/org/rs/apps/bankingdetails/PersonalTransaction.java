package org.rs.apps.bankingdetails;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Temporal;

/**
 *
 * @author ra2ldip
 */
public class PersonalTransaction {

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date txnDate;//[{"txnDate":1443033000000,"txnType":5,"closeAmnt":123.00,"txnAmnt":123,"remAmnt":0,"txnReason":"qwe"}]
    private int txnType;
    private BigDecimal closeAmnt;
    private BigDecimal txnAmnt=BigDecimal.ZERO;
    private BigDecimal remAmnt = BigDecimal.ZERO;
    private String txnReason;

    public PersonalTransaction(Date txnDate, BigDecimal closeAmnt) {
        this.txnDate = txnDate;
        this.closeAmnt = closeAmnt;

    }

    public PersonalTransaction() {
    }

    public BigDecimal getCloseAmnt() {
        return closeAmnt;
    }

    public void setCloseAmnt(BigDecimal closeAmnt) {
        this.closeAmnt = closeAmnt;
    }

    /**
     * @return the txnDate
     */
    public Date getTxnDate() {
        return txnDate;
    }

    /**
     * @param txnDate the txnDate to set
     */
    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
    }

    /**
     * @return the txnType
     */
    public int getTxnType() {
        return txnType;
    }

    /**
     * @param txnType the txnType to set
     */
    public void setTxnType(int txnType) {
        this.txnType = txnType;
    }

    /**
     * @return the txnAmnt
     */
    public BigDecimal getTxnAmnt() {
        return txnAmnt;
    }

    /**
     * @param txnAmnt the txnAmnt to set
     */
    public void setTxnAmnt(BigDecimal txnAmnt) {
        this.txnAmnt = txnAmnt;
    }

    /**
     * @return the remAmnt
     */
    public BigDecimal getRemAmnt() {
        return remAmnt;
    }

    /**
     * @param remAmnt the remAmnt to set
     */
    public void setRemAmnt(BigDecimal remAmnt) {
        this.remAmnt = remAmnt;
    }

    /**
     * @return the txnReason
     */
    public String getTxnReason() {
        return txnReason;
    }

    /**
     * @param txnReason the txnReason to set
     */
    public void setTxnReason(String txnReason) {
        this.txnReason = txnReason;
    }

}
