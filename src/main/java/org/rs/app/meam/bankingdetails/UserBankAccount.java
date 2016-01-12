package org.rs.app.meam.bankingdetails;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.Column;
import javax.persistence.Transient;

/**
 *
 * @author ra2ldip
 */
@Entity
public class UserBankAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    private long bankCode;
    private long acNo;
    private String ifscCode;
    private int acType;
    private BigDecimal avBalance = BigDecimal.ZERO;
    private BigDecimal intRate;
    private int intCycleDays;
    private int userId;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date acOpeningDate;
    @Transient
    private List<PersonalTransaction> txnList;
    @Column(length = 32672)
    private String txnHistory;

    public UserBankAccount() {
        this.txnList = new ArrayList<>();
    }

    public UserBankAccount(long bankCode, long acNo, String ifscCode, int acType, int userId, Date acOpeningDate) {
        this();
        this.bankCode = bankCode;
        this.acNo = acNo;
        this.ifscCode = ifscCode;
        this.acType = acType;
        this.userId = userId;
        this.acOpeningDate = acOpeningDate;
    }

    public BigDecimal getAvBalance() {
        return avBalance;
    }

    public void setAvBalance(BigDecimal avBalance) {
        this.avBalance = avBalance;
    }

    public BigDecimal getIntRate() {
        return intRate;
    }

    public void setIntRate(BigDecimal intRate) {
        this.intRate = intRate;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the acNo
     */
    public long getAcNo() {
        return acNo;
    }

    /**
     * @param acNo the acNo to set
     */
    public void setAcNo(long acNo) {
        this.acNo = acNo;
    }

    /**
     * @return the ifscCode
     */
    public String getIfscCode() {
        return ifscCode;
    }

    /**
     * @param ifscCode the ifscCode to set
     */
    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the acOpeningDate
     */
    public Date getAcOpeningDate() {
        return acOpeningDate;
    }

    /**
     * @param acOpeningDate the acOpeningDate to set
     */
    public void setAcOpeningDate(Date acOpeningDate) {
        this.acOpeningDate = acOpeningDate;
    }

    /**
     * @return the txnList
     */
    public List<PersonalTransaction> getTxnList() {
        if (this.txnHistory != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                this.txnList = mapper.readValue(this.txnHistory, new TypeReference<List<PersonalTransaction>>() {
                });
            } catch (IOException ex) {
                Logger.getLogger(UserBankAccount.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return txnList;
    }

    /**
     * @param txnList the txnList to set
     */
    public void setTxnList(List<PersonalTransaction> txnList) {
        if (txnList != null || !txnList.isEmpty()) {
            ObjectMapper om = new ObjectMapper();
            try {
                this.txnHistory = om.writeValueAsString(txnList);
            } catch (JsonProcessingException ex) {
                Logger.getLogger(UserBankAccount.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.txnList = txnList;
    }

    /**
     * @return the txnHistory
     */
    public String getTxnHistory() {

        return txnHistory;
    }

    /**
     * @param txnHistory the txnHistory to set
     */
    public void setTxnHistory(String txnHistory) {

        this.txnHistory = txnHistory;
    }

    /**
     * @return the acType
     */
    public int getAcType() {
        return acType;
    }

    /**
     * @param acType the acType to set
     */
    public void setAcType(int acType) {
        this.acType = acType;
    }

    /**
     * @return the bankCode
     */
    public long getBankCode() {
        return bankCode;
    }

    /**
     * @param bankCode the bankCode to set
     */
    public void setBankCode(long bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * @return the intCycleDays
     */
    public int getIntCycleDays() {
        return intCycleDays;
    }

    /**
     * @param intCycleDays the intCycleDays to set
     */
    public void setIntCycleDays(int intCycleDays) {
        this.intCycleDays = intCycleDays;
    }
}
