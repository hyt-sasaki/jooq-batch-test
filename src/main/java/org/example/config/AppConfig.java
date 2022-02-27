package org.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("org.example.beans")
@Import({DatasourceConfig.class, BatchConfig.class, BatchJobConfig.class})
public class AppConfig {
}
