package org.rs.app.meam.profile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.rs.app.meam.master.UserMaster;
import org.rs.app.meam.master.dao.UserMasterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ra2ldip
 */
@Controller
public class LoginController {

    @Autowired
    UserMasterRepo userMasterRepo;

    @RequestMapping(value = "/expense/login.htm", method = RequestMethod.GET)
    public String loginPage(Model model) {
        model.addAttribute("user", this);
        return "profile/login";
    }

    @RequestMapping(value = "/expense/login.htm", method = RequestMethod.POST)
    public String submitLoginPage(@RequestParam("userName") String userName, @RequestParam("userPass") String pass, Model m, HttpSession session, HttpServletRequest req) {
        UserMaster obj = userMasterRepo.findByUserNameOrMobileNo(userName, userName);
        if (obj == null || !obj.getUserPass().equals(pass)) {
            m.addAttribute("err", "Invalid Username or Password");
            return "profile/login";
        }
        session.invalidate();
        session = req.getSession(true);
        session.setAttribute("_user_meam_", obj);
        return "redirect:/home.htm";
    }

    @RequestMapping(value = "/home.htm", method = RequestMethod.GET)
    public String homePage(Model model, UserMaster um) {
        model.addAttribute("um", um);
        return "profile/home";
    }

    @RequestMapping(value = "/logoff.htm", method = RequestMethod.GET)
    public String logOff(HttpSession session) {
        session.invalidate();
        return "redirect:/expense/login.htm";
    }

    @RequestMapping(value = "/ems/system/info.htm", method = RequestMethod.GET)

    public void getHello(@RequestHeader("host") String hostName,
            @RequestHeader("Accept") String acceptType,
            @RequestHeader("Accept-Language") String acceptLang,
            @RequestHeader("Accept-Encoding") String acceptEnc,
            @RequestHeader("Cache-Control") String cacheCon,
            @RequestHeader("Cookie") String cookie,
            @RequestHeader("User-Agent") String userAgent, Model model, HttpServletResponse response) throws IOException {
        File file = new File("D:/SystemInfo.txt");
        BufferedWriter wr = new BufferedWriter(new FileWriter(file));
        wr.write("System Information");
        wr.newLine();
        wr.write("Host " + hostName);
        wr.newLine();
        wr.write("Accept :" + acceptType);
        wr.newLine();
        wr.write("Accept Language :" + acceptLang);
        wr.newLine();
        wr.write("Accept Encoding :" + acceptEnc);
        wr.newLine();
        wr.write("Cache-Control :" + cacheCon);
        wr.newLine();
        wr.write("Cookie :" + cookie);
        wr.newLine();
        wr.write("User-Agent :" + userAgent);
        wr.close();
        Path path = Paths.get("D:/SystemInfo.txt");
        byte[] data = Files.readAllBytes(path);
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setHeader("Accept-Ranges", "none");
        response.addHeader("Content-Disposition", "attachment; filename=System Info.txt");
        OutputStream os = response.getOutputStream();
        os.write(data);
        os.flush();
        os.close();

    }

}
