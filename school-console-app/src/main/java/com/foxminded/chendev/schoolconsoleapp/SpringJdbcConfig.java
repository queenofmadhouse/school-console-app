package com.foxminded.chendev.schoolconsoleapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("com.foxminded.chendev.schoolconsoleapp.dao")
public class SpringJdbcConfig {

    @Bean
    public DriverManagerDataSource postgresqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5433/school");
        dataSource.setUsername("postgres");
        dataSource.setPassword("1234");

        return dataSource;
    }
}
