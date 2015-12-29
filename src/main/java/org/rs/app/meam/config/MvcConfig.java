package org.rs.app.meam.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import org.iitkgp.ccms.framework.UserObjectResolver;
//import org.iitkgp.ccms.utils.web.ButtonsNamesResolver;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles2.TilesConfigurer;
import org.springframework.web.servlet.view.tiles2.TilesView;

/**
 *
 * @author srini
 */
@Configurable
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {
    // @Autowired
    //private ApplicationInfoController applicationInfoController; // Used to provide version information on all JSP's.

    private List<HttpMessageConverter<?>> messageConverters; // Cached: this is not a bean.

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        // Note: this overwrites the default message converters.
        converters.addAll(getMessageConverters());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
        registry.addFormatterForFieldType(Date.class, new DateFormatter("dd/MM/yyyy"));
    }

    /**
     * The message converters for the content types we support.
     *
     * @return the message converters; returns the same list on subsequent calls
     */
    private List<HttpMessageConverter<?>> getMessageConverters() {

        if (messageConverters == null) {
            messageConverters = new ArrayList<HttpMessageConverter<?>>();
            ObjectMapper o = new ObjectMapper();
            o.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            o.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
//           
            Jackson2ObjectMapperFactoryBean j = new Jackson2ObjectMapperFactoryBean();
            j.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            //o.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            List<MediaType> mt = new ArrayList<MediaType>();
            mt.add(MediaType.APPLICATION_JSON);
//            mt.add(MediaType.MULTIPART_FORM_DATA);
            mt.add(MediaType.ALL);
//            mt.add(MediaType.APPLICATION_FORM_URLENCODED);
            mappingJacksonHttpMessageConverter.setSupportedMediaTypes(mt);
            mappingJacksonHttpMessageConverter.setObjectMapper(o);

            messageConverters.add(mappingJacksonHttpMessageConverter);
        }
        return messageConverters;
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false).
                favorParameter(true).
                parameterName("mediaType").
                ignoreAcceptHeader(true).
                useJaf(false).
                defaultContentType(MediaType.APPLICATION_JSON).
                mediaType("xml", MediaType.APPLICATION_XML).
                mediaType("json", MediaType.APPLICATION_JSON);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

        // Extra argument resolvers (the default ones are added as well).
        // user it to insert user object etc as arguments        
//        argumentResolvers.add(new UserObjectResolver());
//        argumentResolvers.add(new ButtonsNamesResolver());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("/resources/images/");
        registry.addResourceHandler("/scripts/**").addResourceLocations("/resources/scripts/");
        registry.addResourceHandler("/applets/**").addResourceLocations("/resources/applets/");
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver cf = new CommonsMultipartResolver();
        cf.setMaxUploadSize(50 * 1024 * 1024);
        return cf;
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(new String[]{"/WEB-INF/tiles-defs.xml"});
        tilesConfigurer.setPreparerFactoryClass(org.springframework.web.servlet.view.tiles2.SpringBeanPreparerFactory.class);
        return tilesConfigurer;
    }

    @Bean
    public ViewResolver viewResolver() {
        UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
        viewResolver.setViewClass(TilesView.class);
        return viewResolver;
    }
}
