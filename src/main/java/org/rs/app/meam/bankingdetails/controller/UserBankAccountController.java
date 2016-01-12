package org.rs.app.meam.bankingdetails.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.rs.app.meam.common.UserBankOptions;
import org.rs.app.meam.bankingdetails.PersonalTransaction;
import org.rs.app.meam.bankingdetails.UserBankAccount;
import org.rs.app.meam.bankingdetails.dao.BankMasterRepo;
import org.rs.app.meam.bankingdetails.dao.UserBankAccountRepo;
import org.rs.app.meam.bankingdetails.service.BankingHelper;
import org.rs.app.meam.master.UserMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ra2ldip
 */
@Controller
public class UserBankAccountController {

    @Autowired
    UserBankAccountRepo accountRepo;
    @Autowired
    BankMasterRepo bankMasterRepo;

    @RequestMapping(value = "/ems/allAccountList.htm", method = RequestMethod.GET)
    public String getAllAcDetails(Model model, UserMaster um) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        model.addAttribute("bankList", mapper.writeValueAsString(BankingHelper.getAllBank(bankMasterRepo)));
        model.addAttribute("accType", mapper.writeValueAsString(UserBankOptions.accType));
        model.addAttribute("crByDr", mapper.writeValueAsString(UserBankOptions.crBydr));
        return "bankingdetails/allAccDetails";
    }

    @RequestMapping(value = "/getAllDetails.htm")
    public @ResponseBody
    List<UserBankAccount> getAll(UserMaster um) {
        return accountRepo.findByUserId(um.getUserId());
    }

    @RequestMapping(value = "/addEditBank.htm")
    public @ResponseBody
    boolean bankDetailsUpdation(@RequestParam Object id, @RequestParam long bankCode, @RequestParam long acNo,
            @RequestParam String ifscCode, @RequestParam int acType, @RequestParam String oper, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String acOpeningDate, UserMaster um) throws ParseException {
        UserBankAccount uba = null;
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date ulDate = (Date) formatter.parse(acOpeningDate);
        switch (oper) {
            case "add":
                uba = accountRepo.findByBankCodeAndAcTypeAndAcNoAndUserId(bankCode, acType, acNo, um.getUserId());
                if (uba != null) {
                    return false;
                }
                uba = new UserBankAccount(bankCode, acNo, ifscCode, acType, um.getUserId(), ulDate);
                break;
            case "edit":
                uba = accountRepo.findOne(Long.parseLong(id.toString()));
                if (uba == null) {
                    return false;
                }
                uba.setBankCode(bankCode);
                uba.setIfscCode(ifscCode);
                uba.setAcNo(acNo);
                uba.setAcType(acType);
                break;
        }
        if (uba != null) {
            accountRepo.saveAndFlush(uba);
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/deleteBank.htm", method = RequestMethod.POST)
    public @ResponseBody
    void deleteAccount(@RequestParam long id) {
        UserBankAccount uba = accountRepo.findOne(id);
        if (uba.getTxnList() != null || uba.getTxnList().isEmpty()) {
            accountRepo.delete(uba);
        }
    }

    @RequestMapping(value = "/ems/addTxn.htm", method = RequestMethod.GET)
    public String addTxn(@RequestParam Long bId, UserMaster um, Model model) {
        UserBankAccount uba = accountRepo.findOne(bId);
        if (uba == null || uba.getUserId() != um.getUserId()) {
            throw new RuntimeException("No bank details found");
        }
        return getJSPforTXN(uba,new PersonalTransaction(new Date(), uba.getAvBalance()),model);
    }

    private String getJSPforTXN(UserBankAccount uba,  PersonalTransaction pt,Model model) {
        model.addAttribute("accType", UserBankOptions.accType);
        model.addAttribute("bankName", BankingHelper.getBankById(uba.getBankCode(), bankMasterRepo));
        model.addAttribute("uba", uba);
        model.addAttribute("txn", pt);
        model.addAttribute("txnType", UserBankOptions.crBydr);
        return "bankingdetails/addTxn";
    }

    @Transactional
    @RequestMapping(value = "/ems/addTxn.htm", method = RequestMethod.POST)
    public String setAddedTxn(@RequestParam Long bId, @ModelAttribute PersonalTransaction transaction, Model m, BindingResult result) {
        UserBankAccount uba = accountRepo.findOne(bId);
        validateTxn(transaction, result);
        if (result.hasErrors()) {
            return getJSPforTXN(uba, transaction,m);
        }
        if (UserBankOptions.creditGroup.containsKey(transaction.getTxnType())) {
            BankingHelper.calCulateAndSetCredit(accountRepo, uba, transaction);
        } else if (UserBankOptions.debitGroup.containsKey(transaction.getTxnType())) {
            BankingHelper.calCulateAndSetDebit(accountRepo, uba, transaction);
        }
        return "redirect:/ems/allAccountList.htm";
    }

    private void validateTxn(PersonalTransaction transaction, Errors e) {
        if (transaction.getTxnAmnt() == BigDecimal.ZERO) {
            e.rejectValue("txnAmnt", "100", "Please add transaction ammount");
        }
        if (!StringUtils.hasText(transaction.getTxnReason())) {
            e.rejectValue("txnReason", "200", "Please put transaction details");
        }
    }
}
