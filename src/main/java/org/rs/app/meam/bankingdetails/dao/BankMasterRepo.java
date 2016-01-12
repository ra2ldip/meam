package org.rs.app.meam.bankingdetails.dao;

import org.rs.app.meam.bankingdetails.BankMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ra2ldip
 */
public interface BankMasterRepo extends JpaRepository<BankMaster, Long> {

    BankMaster findByBankName(String bankName);
}
