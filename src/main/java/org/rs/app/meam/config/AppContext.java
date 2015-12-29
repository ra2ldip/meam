package org.rs.app.meam.config;

import org.springframework.context.ApplicationContext;

/**
 * This class provides application-wide access to the Spring ApplicationContext.
 * The ApplicationContext is injected by the class "ApplicationContextProvider".
 *
 * @author Sintu Pal
 */
public class AppContext {

    private static ApplicationContext ctx;
    private static String imagePath;
    private static String fontPath;

    /**
     * Injected from the class "ApplicationContextProvider" which is
     * automatically loaded during Spring-Initialization.
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }

    /**
     * Get access to the Spring ApplicationContext from everywhere in your
     * Application.
     *
     * @return application contrext
     */
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    /**
     * gets a spring bean from spring application context
     *
     * @param beanName the name of the bean to be fetched
     * @return the bean
     */
    public static <T> T getSpringBean(String beanName) {
        return (T) ctx.getBean(beanName);
    }

    /**
     * gets a spring bean from spring application context
     *
     * @param class of the bean to be fetched
     * @return the bean
     */
    public static <T> T getSpringBean(Class<T> reqClass) {
        return (T) ctx.getBean(reqClass);
    }

    /**
     * @return the imagePath
     */
    public static String getImagePath() {
        return imagePath;
    }

    /**
     * @param aImagePath the imagePath to set
     */
    public static void setImagePath(String aImagePath) {
        System.out.println("setting up image path="+aImagePath);
        imagePath = aImagePath;
    }

    /**
     * @return the fontPath
     */
    public static String getFontPath() {
        return fontPath;
    }

    /**
     * @param aFontPath the fontPath to set
     */
    public static void setFontPath(String aFontPath) {
        fontPath = aFontPath;
    }
}
