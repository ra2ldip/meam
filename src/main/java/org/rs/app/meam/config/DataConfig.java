package org.rs.app.meam.config;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author srini
 */
@Configuration
@EnableJpaRepositories( )
@EnableTransactionManagement
public class DataConfig {

//    @Autowired
//    DataSource dataSource;
    
    /*@Bean(name = "dataSource")
    public DataSource dataSource() {
        try {
            Context ctx = new InitialContext();
            return (DataSource) ctx.lookup("java:comp/env/jdbc/OracleDS");
        } catch (NamingException ne) {
            throw new RuntimeException(ne);
        }
    }
    */
        
    public @Bean
    FactoryBean dataSourceFactory() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:comp/env/jdbc/OracleDS");
        jndiObjectFactoryBean.setCache(true);
        jndiObjectFactoryBean.setLookupOnStartup(true);
        jndiObjectFactoryBean.setProxyInterface(javax.sql.DataSource.class);
        return jndiObjectFactoryBean;
    }


    
//    @Bean
//    public EntityManager  entityManager() throws Exception{
//        return entityManagerFactory().createEntityManager();
//    }
    
    @Bean
    public JdbcTemplate jdbcTemplate() throws NamingException, Exception {
        DataSource ds = (DataSource) dataSourceFactory().getObject();
        return new JdbcTemplate(ds);
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() throws Exception {
      DataSource ds = (DataSource) dataSourceFactory().getObject();
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        //vendorAdapter.setDatabase(Database.ORACLE);
        vendorAdapter.setDatabase(Database.DERBY);
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.DerbyTenSevenDialect");
//        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.Oracle10gDialect");
//        vendorAdapter.setDatabase(Database.ORACLE);
        vendorAdapter.setShowSql(true);
       

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("org.rs.app");
        factory.setDataSource(ds);
        
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public org.springframework.orm.hibernate4.HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new org.springframework.orm.hibernate4.HibernateExceptionTranslator();
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }
}
