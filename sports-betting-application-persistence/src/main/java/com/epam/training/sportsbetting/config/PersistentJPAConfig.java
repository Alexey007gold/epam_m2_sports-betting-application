package com.epam.training.sportsbetting.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableJpaRepositories("com.epam.training.sportsbetting.repository")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class PersistentJPAConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setPackagesToScan("com.epam.training.sportsbetting.entity");
        em.setJpaProperties(additionalProperties(env));

        return em;
    }

    @Bean
    @Profile("!test")
    public DataSource dataSource(Environment env) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUser(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }

    @Bean
    @Profile("test")
    public DataSource testDataSource(Environment env) {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUser(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    private Properties additionalProperties(Environment env) {
        Properties properties = new Properties();
        addPropIfNotNull(env, properties, "hibernate.hbm2ddl");
        addPropIfNotNull(env, properties, "hibernate.hbm2ddl.auto");
        addPropIfNotNull(env, properties, "hibernate.dialect");
        addPropIfNotNull(env, properties, "hibernate.ejb.naming_strategy");
        addPropIfNotNull(env, properties, "hibernate.show_sql");
        addPropIfNotNull(env, properties, "hibernate.format_sql");
        addPropIfNotNull(env, properties, "hibernate.default_schema");
        return properties;
    }

    private void addPropIfNotNull(Environment env, Properties properties, String propName) {
        String val = env.getProperty(propName);
        if (val != null) {
            properties.setProperty(propName, val);
        }

    }
}