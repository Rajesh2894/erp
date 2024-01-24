package com.abm.mainet.config;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = { "com.abm.mainet.bpm.repository",
        "com.abm.mainet.brms.repository" }, entityManagerFactoryRef = "entityManagerFactory")
@EnableTransactionManagement
@EnableSpringDataWebSupport
public class DatabaseConfig {

    @Autowired
    private Environment env;

    @Bean
    public EntityManagerFactory entityManagerFactory() throws NamingException {

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.valueOf(env.getProperty("database.name")));
        jpaVendorAdapter.setDatabasePlatform(env.getProperty("database.platform"));
        jpaVendorAdapter.setShowSql(env.getProperty("database.showSql", Boolean.class));
        jpaVendorAdapter.setGenerateDdl(env.getProperty("database.generateDdl", Boolean.class));

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[] { "com.abm.mainet.brms.entity", "com.abm.mainet.bpm.domain" });
        em.setJpaVendorAdapter(jpaVendorAdapter);
        em.afterPropertiesSet();

        return em.getObject();
    }

    @Bean
    public DataSource dataSource() throws NamingException {
        String jndiName = env.getProperty("database.dataSource.jndi");
        DataSource dataSource = (DataSource) new JndiTemplate().lookup(jndiName);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        transactionManager.setNestedTransactionAllowed(true);
        return transactionManager;
    }
}
