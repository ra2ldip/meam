package org.rs.apps.expense.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import org.rs.apps.expense.DailyExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author ra2ldip
 */
public interface DailyExpenseDao extends JpaRepository<DailyExpense, Long> {

    List<DailyExpense> findByUserIdAndDate(long userId, Date date);

    @Query("select d.walletBalance from DailyExpense d where d.id=(select max(x.id) from DailyExpense x where x.userId=?)")
    BigDecimal getLatestWalBalByUserId(long userId);
}
