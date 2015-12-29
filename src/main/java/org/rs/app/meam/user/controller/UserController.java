
package org.rs.app.meam.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author ratul
 */
@Controller
public class UserController {
    @RequestMapping(value = "/login.htm", method = RequestMethod.GET)
    public String loginPage(){
        return "user/loginPage";
    }
    
}
