package com.epam.training.sportsbetting.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class LiquibaseConfig {

    private final DataSource dataSource;

    private final ResourceLoader resourceLoader;

    @Autowired
    public LiquibaseConfig(DataSource dataSource, ResourceLoader resourceLoader) {
        this.dataSource = dataSource;
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public SpringLiquibase liquibase(Environment env) {
        // Locate change log file
        String changelogFile = "classpath:lqb/liquibase-changelog.xml";
        Resource resource = resourceLoader.getResource(changelogFile);
        Assert.state(resource.exists(), "Unable to find file: " + changelogFile);

        // Configure Liquibase
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(changelogFile);
        liquibase.setContexts("test,dev,prod");
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema(env.getProperty("db.schema"));
        liquibase.setDropFirst(false);
        liquibase.setShouldRun(true);

        // Verbose logging
        Map<String, String> params = new HashMap<>();
        params.put("verbose", "true");
        liquibase.setChangeLogParameters(params);

        return liquibase;
    }
}