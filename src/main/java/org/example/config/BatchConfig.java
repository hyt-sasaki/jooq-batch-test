package org.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    @Bean
    @Primary
    public DataSource metaDatasource() {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://127.0.0.1:13306/batch");

        config.setUsername("docker");
        config.setPassword("docker");

        config.setPoolName("METADATA-POOL");
        config.setMaximumPoolSize(2);

        return new HikariDataSource(config);
    }
}
