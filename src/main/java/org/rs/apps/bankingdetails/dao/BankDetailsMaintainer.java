package org.rs.apps.bankingdetails.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import org.rs.apps.bankingdetails.PersonalTransaction;

/**
 *
 * @author ra2ldip
 */
public interface BankDetailsMaintainer {

    public BigDecimal getTxn();

    public BigDecimal getInterest();

    public List<PersonalTransaction> getTxnHistory(Date toDt, Date formDt);
}
