package org.rs.apps.bankingdetails.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.rs.app.meam.common.UserBankOptions;
import org.rs.apps.bankingdetails.BankMaster;
import org.rs.apps.bankingdetails.PersonalTransaction;
import org.rs.apps.bankingdetails.UserBankAccount;

import org.rs.apps.bankingdetails.dao.BankMasterRepo;
import org.rs.apps.bankingdetails.dao.UserBankAccountRepo;

/**
 *
 * @author ra2ldip
 */
public class BankingHelper {

    public static Map<Long, String> getAllBank(BankMasterRepo bankMasterRepo) {
        Map<Long, String> allBank = new HashMap<>();
        allBank.put(-999l, "Others");
        List<BankMaster> bms = bankMasterRepo.findAll();
        if (bms != null || !bms.isEmpty()) {
            for (BankMaster bm : bms) {
                allBank.put(bm.getId(), bm.getBankName() + " [" + UserBankOptions.bankType.get(bm.getType()) + "]");
            }
        }
        return allBank;
    }

    public static String getBankById(long id, BankMasterRepo bankMasterRepo) {
        return bankMasterRepo.findOne(id).getBankName();
    }

    public static void calCulateAndSetCredit(UserBankAccountRepo accountRepo, UserBankAccount uba, PersonalTransaction transaction) {
        uba.setAvBalance(uba.getAvBalance().add(transaction.getTxnAmnt()));
        transaction.setCloseAmnt(uba.getAvBalance());
        List<PersonalTransaction> pts = uba.getTxnList();
        pts.add(transaction);
        uba.setTxnList(pts);
        accountRepo.save(uba);
    }

    public static void calCulateAndSetDebit(UserBankAccountRepo accountRepo, UserBankAccount uba, PersonalTransaction transaction) {
        uba.setAvBalance(uba.getAvBalance().subtract(transaction.getTxnAmnt()));
        transaction.setCloseAmnt(uba.getAvBalance());
        List<PersonalTransaction> pts = uba.getTxnList();
        pts.add(transaction);
        uba.setTxnList(pts);
        accountRepo.save(uba);
    }
}
