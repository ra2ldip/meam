package org.rs.app.meam.bankingdetails.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import org.rs.app.meam.bankingdetails.PersonalTransaction;

/**
 *
 * @author ra2ldip
 */
public interface BankDetailsMaintainer {

    public BigDecimal getTxn();

    public BigDecimal getInterest();

    public List<PersonalTransaction> getTxnHistory(Date toDt, Date formDt);
}
