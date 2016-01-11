package org.rs.app.bankingdetails.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.rs.app.bankingdetails.PersonalTransaction;
import org.rs.app.bankingdetails.dao.BankDetailsMaintainer;

/**
 *
 * @author ra2ldip
 */
public class BankDebitHelper implements BankDetailsMaintainer {

    public BankDebitHelper(BigDecimal avlAmnt, BigDecimal txnAmnt) {
    }

    public BankDebitHelper(BigDecimal avlAmnt, int cycleDays, BigDecimal intRate) {
    }

    public BankDebitHelper() {
    }

    @Override
    public BigDecimal getTxn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getInterest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PersonalTransaction> getTxnHistory(Date toDt, Date formDt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
