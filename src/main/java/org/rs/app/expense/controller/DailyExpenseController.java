package org.rs.app.expense.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.rs.app.meam.common.ExpenseOptions;
import org.rs.app.meam.common.JSONHelper;
import org.rs.app.expense.DailyExpense;
import org.rs.app.expense.dao.DailyExpenseDao;
import org.rs.app.master.UserMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ra2ldip
 */
@Controller
public class DailyExpenseController {

    private static final int WALLET_ADD = 1;
    private static final int WALLET_SUBSTRUCT = 2;
    @Autowired
    DailyExpenseDao expenseDao;

    @RequestMapping(value = "/ems/daily.htm", method = RequestMethod.GET)
    public String expenseChartByDate(Model m) {
        m.addAttribute("txnType", JSONHelper.obectToJSON(ExpenseOptions.EXPENSE));
        m.addAttribute("expType", JSONHelper.obectToJSON(ExpenseOptions.EXP_TXN_TYPE));
        return "expense/expenseChart";
    }

    @RequestMapping(value = "/ems/daily/date/{date}", method = RequestMethod.GET)
    public @ResponseBody
    List getExpByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date, UserMaster um, HttpServletRequest request) throws ParseException {
        System.out.println("S L :" + request.toString());
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date ulDate = (Date) formatter.parse(date);
        return expenseDao.findByUserIdAndDate(um.getUserId(), ulDate);
    }

    @RequestMapping(value = "/ems/daily/getWalBalLatest.htm", method = RequestMethod.GET)
    public @ResponseBody
    BigDecimal getLatestWalBal(UserMaster um) {
        return expenseDao.getLatestWalBalByUserId(um.getUserId());
    }

    @RequestMapping(value = "/addEditExpnse/{date}")
    public @ResponseBody
    boolean dailyUpadtion(@RequestParam Object id, @RequestParam BigDecimal expnse, @RequestParam String expDetails,
            @RequestParam int expType, @RequestParam int txnType, @RequestParam String oper, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date, UserMaster um) throws ParseException {
        DailyExpense de = null;
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date ulDate = (Date) formatter.parse(date);
        switch (oper) {
            case "add":
                de = new DailyExpense(um.getUserId(), ulDate, txnType, expType, expnse, expDetails);
                break;
            case "edit":
                de = expenseDao.findOne(Long.parseLong(id.toString()));
                if (de == null) {
                    return false;
                }
                de.setExpnse(expnse);
                de.setExpType(expType);
                de.setTxnType(txnType);
                break;
        }
        if (de != null) {
            if (expType != ExpenseOptions.WALLET_RECHARGE) {
                de.setWalletBalance(calculateWalletBallance(de.getExpnse(), WALLET_SUBSTRUCT, um));
            } else {
                de.setWalletBalance(calculateWalletBallance(de.getExpnse(), WALLET_ADD, um));
            }
            expenseDao.saveAndFlush(de);
            return true;
        }
        return false;
    }

    private BigDecimal calculateWalletBallance(BigDecimal exp, int oper, UserMaster um) {
        BigDecimal latestWallBall = expenseDao.getLatestWalBalByUserId(um.getUserId());
        if (latestWallBall == null) {
            latestWallBall = BigDecimal.ZERO;
        }
        switch (oper) {
            case WALLET_ADD:
                latestWallBall = latestWallBall.add(exp);
                break;
            case WALLET_SUBSTRUCT:
                latestWallBall = latestWallBall.subtract(exp);
                break;
        }
        return latestWallBall;
    }

    @RequestMapping(value = "/deleteExp.htm", method = RequestMethod.POST)
    public @ResponseBody
    void deleteExpense(@RequestParam long id, UserMaster um) {
        DailyExpense de = expenseDao.findOne(id);
        de.setWalletBalance(calculateWalletBallance(de.getExpnse(), WALLET_ADD, um));
        de.setActiveState(DailyExpense.DELETED);
        expenseDao.saveAndFlush(de);
    }
}
