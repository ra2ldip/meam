package org.rs.app.meam.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 * @author Sintu Pal
 */
@Component
public class ApplicaitonContextProvider implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
         AppContext.setApplicationContext(applicationContext);
    }
}
