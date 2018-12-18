package com.neueda.url.shortner.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class DbConfig {

    @Resource
    ApplicationConfig applicationConfig;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(applicationConfig.getJdbcDriverClassName());
        dataSource.setUrl(applicationConfig.getJdbcUrl());
        dataSource.setUsername(applicationConfig.getJdbcUsername());
        dataSource.setPassword(applicationConfig.getJdbcPassword());
        return dataSource;
    }

}
