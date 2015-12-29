package org.rs.app.meam.config;

import java.io.File;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * @author ratul
 */
public class Initiliazer implements WebApplicationInitializer {

   
    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
        mvcContext.register(AppConfig.class);
        ServletRegistration.Dynamic dispatcher = sc.addServlet("dispatcher", new DispatcherServlet(mvcContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        EnumSet<DispatcherType> e = EnumSet.of(DispatcherType.REQUEST);
        sc.addFilter("securityFilter", new SecurityFilter(mvcContext)).addMappingForServletNames(e, true, "dispatcher");
        AppContext.setImagePath(sc.getRealPath("/resources/images"));
        AppContext.setFontPath(sc.getRealPath("/resources/scripts/font"));
        AppContext.setApplicationContext(mvcContext);
        //sc.addListener(ContextLoaderListener.class);
    }
    
}
