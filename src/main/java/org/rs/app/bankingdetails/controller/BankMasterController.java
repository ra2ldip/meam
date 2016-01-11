package org.rs.app.bankingdetails.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.rs.app.meam.common.UserBankOptions;
import org.rs.app.bankingdetails.BankMaster;
import org.rs.app.bankingdetails.dao.BankMasterRepo;
import org.rs.app.master.UserMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ra2ldip
 */
@Controller
public class BankMasterController {

    @Autowired
    BankMasterRepo bankMasterRepo;

    @RequestMapping(value = "/ems/bankMaster.htm", method = RequestMethod.GET)
    public String getAllAcDetails(Model model, UserMaster um) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        model.addAttribute("accType", mapper.writeValueAsString(UserBankOptions.bankType));
//        model.addAttribute("allbank", mapper.writeValueAsString(bankMasterRepo.findAll()));
        return "bankingdetails/bankMaster";
    }

    @RequestMapping(value = "/bankAll.htm")
    public @ResponseBody
    List<BankMaster> getAllBank() {
        return bankMasterRepo.findAll();
    }

    @RequestMapping(value = "/bankMasterUpdation.htm", method = RequestMethod.POST)
    public @ResponseBody
    String bankMasterUpdation(@RequestParam String oper, @RequestParam Object id,
    @RequestParam (required = false) String bankName,
    @RequestParam (required = false) Integer type) {
        boolean done=true;
        BankMaster bm=null;
        switch (oper) {
            case "add":
                bm=bankMasterRepo.findByBankName(bankName);
                if(bm!=null){
                    return "Bank is listed";
                }
                bm=new BankMaster(bankName, type);
                bankMasterRepo.saveAndFlush(bm);
                break;
            case "edit":
                bm=bankMasterRepo.findOne(Long.parseLong(id.toString()));
                bm.setBankName(bankName);
                bm.setType(type);
                bankMasterRepo.saveAndFlush(bm);
                break;
            case "del":
                bankMasterRepo.delete(Long.parseLong(id.toString()));
                break;
        }
        return "__DONE__";
    }
}
