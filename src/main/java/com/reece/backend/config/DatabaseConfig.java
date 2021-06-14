package com.reece.backend.config;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.DefaultJpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

/**
 * Created by tolim on 3/07/2018.
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@EnableTransactionManagement
@Slf4j
public class DatabaseConfig {

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public HikariDataSource dataSource(DataSourceProperties properties) {
        return (HikariDataSource) properties.initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean
    public SpringLiquibase liquibase(HikariDataSource dataSource, @Value("${spring.liquibase.change-log}") String changeLog, @Value("${spring.liquibase.contexts}") String contexts) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(changeLog);
        liquibase.setContexts(contexts);
        liquibase.setShouldRun(true);
        return liquibase;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(HikariDataSource dataSource, @Value("${app.database.type}") Database databaseType, @Value("${app.database.platform}") String databasePlatform) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPackagesToScan("com.reece.backend.model.entity");
        entityManagerFactory.setPersistenceUnitName("reece-backend");
        entityManagerFactory.setDataSource(dataSource);

        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(databaseType);
        hibernateJpaVendorAdapter.setDatabasePlatform(databasePlatform);
        entityManagerFactory.setJpaVendorAdapter(hibernateJpaVendorAdapter);

        entityManagerFactory.setJpaProperties(additionalProperties());
        entityManagerFactory.setJpaDialect(new DefaultJpaDialect());

        return entityManagerFactory;
    }

    @Bean
    @DependsOn("liquibase")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.validator.apply_to_ddl", "false");
        properties.setProperty("hibernate.validator.autoregister_listeners", "false");
        properties.setProperty("javax.persistence.validation.mode", "none");
        properties.setProperty("hibernate.show_sql", "false");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.cache.use_second_level_cache", "false");
        properties.setProperty("hibernate.cache.use_query_cache", "false");
        properties.setProperty("hibernate.generate_statistic", "true");
        properties.setProperty("hibernate.jdbc.batch_size", "5");
        properties.setProperty("hibernate.order_inserts", "true");
        properties.setProperty("hibernate.order_updates", "true");
        properties.setProperty("hibernate.batch_versioned_data", "true");
        return properties;
    }
}