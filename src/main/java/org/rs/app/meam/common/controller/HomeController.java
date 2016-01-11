package org.rs.app.meam.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author ratul
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/welcome.htm", method = RequestMethod.GET)
    public String welcomePage(Model model) {
        StringBuilder user = new StringBuilder();

        user.append("Guest");

        model.addAttribute("user", user.toString());
        return "common/welcome";
    }
}
