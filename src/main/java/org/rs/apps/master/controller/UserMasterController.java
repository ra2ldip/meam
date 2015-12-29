package org.rs.apps.master.controller;

import org.rs.app.meam.common.MEAMException;
import org.rs.apps.master.UserMaster;
import org.rs.apps.master.dao.UserMasterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ra2ldip
 */
@Controller
public class UserMasterController {

    @Autowired
    UserMasterRepo userMasterRepo;

    @RequestMapping(value = "/registration.meam.htm", method = RequestMethod.GET)
    public String userRegistration(Model model) {
        model.addAttribute("userMaster", new UserMaster());
        return "user/userRegistration";
    }

    @RequestMapping(value = "/registration.meam.htm", method = RequestMethod.POST)
    public String createUser(@ModelAttribute UserMaster um, BindingResult result) {
        validateUser(um, result);
        if (result.hasErrors()) {
            return "user/userRegistration";
        }
        um.setActive(true);
        um = userMasterRepo.save(um);
        return "redirect:/ems/profile.htm?id=" + um.getUserId();
    }

    private void validateUser(UserMaster um, Errors e) {
        if (userMasterRepo.findByUserNameOrMobileNo(um.getUserName(), um.getMobileNo()) != null) {
            e.rejectValue("userName", null, "User seems to be exist");
        }
    }

    @RequestMapping(value = "/ems/profile.htm", method = RequestMethod.GET)
    public String profileView(@RequestParam int id, Model model) {
        UserMaster um = userMasterRepo.findOne(id);
        if (um == null) {
            model.addAttribute("noUser", MEAMException.NO_USER_FOUND);
        } else {
            model.addAttribute("userMaster", um);
        }
        return "user/profileView";
    }
}
