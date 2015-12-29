package org.rs.app.meam.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author ratul
 */
public class SecurityFilter implements Filter {

    ApplicationContext ac;

    public SecurityFilter() {
    }

    public SecurityFilter(ApplicationContext ac) {
        this.ac = ac;
    }

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest freq, ServletResponse fres, FilterChain fc) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) freq;
        HttpServletResponse res = (HttpServletResponse) fres;
        String url = req.getRequestURI();
        
        if ( url.startsWith("/css/") || url.startsWith("/images/") || url.startsWith("/scripts/")|| url.startsWith("/applets/") || url.startsWith("/eprocws/")||url.startsWith("/wordService/")||url.startsWith("/registration.meam.htm")) {
            // check if already logged in
        }else if("/login.htm".equals(url) ) {
            if (req.getSession().getAttribute("_user_meam_") != null) {
                res.sendRedirect("/home.htm");
            }
        }else {
            if (req.getSession().getAttribute("_user_meam_") == null) {
                res.sendRedirect("/login.htm");
                return;
            }
        }
        //chain
        fc.doFilter(req, res);

    }

    @Override
    public void destroy() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
