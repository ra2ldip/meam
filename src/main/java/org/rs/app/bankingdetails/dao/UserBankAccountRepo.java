package org.rs.app.bankingdetails.dao;

import java.util.List;
import org.rs.app.bankingdetails.UserBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ra2ldip
 */
public interface UserBankAccountRepo extends JpaRepository<UserBankAccount, Long> {

    List<UserBankAccount> findByUserId(int userId);

    UserBankAccount findByBankCodeAndAcTypeAndAcNoAndUserId(long bankCode, int acType, long acNo, int userId);
}
