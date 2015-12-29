package org.rs.app.meam.config;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 *
 * @author ratul
 */
@Configuration
@ComponentScan(basePackages = "org.rs.app", excludeFilters = {
    @ComponentScan.Filter(Configuration.class)})
@Import({DataConfig.class, MvcConfig.class})
//@EnableScheduling
@EnableCaching
// uncomment if required
//@ImportResource("classpath:properties-config.xml")
//@ImportResource(value = {"classpath:eprocTemplates.xml","classpath:messaingTempletes.xml"})
public class AppConfig {
    
     public PropertyPlaceholderConfigurer propertyConfigurer() throws IOException {
        PropertyPlaceholderConfigurer props = new PropertyPlaceholderConfigurer();
        props.setLocations(new Resource[]{new ClassPathResource("instance.properties")});
        return props;
    }

    @Bean
    public CacheManager cacheManager() {
        // configure and return an implementation of Spring's CacheManager SPI
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("gmenu")));
        return cacheManager;
    }

//    @Bean
//    public JavaMailSender customMailSender() {
//        String host = "gmail1.com";
//        JavaMailSenderImpl j= new JavaMailSenderImpl();
//        j.setHost(host);
//        return j;
//       
//    }

//    @Bean
//    public WorkflowReposPostProcessor process() {
//        return new WorkflowReposPostProcessor();
//    }

//    @Bean
//    public FileUploadService fileUploadService() {
//        String fname = System.getProperty("user.home") + "/UploadFile/";
//        File f = new File(fname);
//
//        if (!(f.exists() && f.isDirectory())) {
//            f.mkdir();
//        }
//        FileUploadService.setTmpPath(fname);
//        System.out.println("setting file upload path=" + fname);
//        return new FileUploadService();
//    }
}
