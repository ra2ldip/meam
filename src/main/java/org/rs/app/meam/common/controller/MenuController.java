package org.rs.app.meam.common.controller;

import java.util.List;
import org.rs.app.meam.common.JSONHelper;
import org.rs.app.meam.common.Menu;
import org.rs.app.meam.common.repo.MenuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author ratul
 */
@Controller
public class MenuController {

    @Autowired
    MenuRepo menuRepo;

    @RequestMapping(value = "/menu/manager.htm", method = RequestMethod.GET)
    public String menuPage(Model model) {
        List<Menu> menus = menuRepo.findAll();
        menus.add(new Menu(Menu.TOP_PARENT, "/#"));
        model.addAttribute("menus", JSONHelper.obectToJSON(menus));
        return "common/menuPage";
    }
}
