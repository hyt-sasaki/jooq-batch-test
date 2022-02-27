package org.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.ResultSet.HOLD_CURSORS_OVER_COMMIT;

@Configuration
public class DatasourceConfig {

    @Bean("readOnlyDatasource")
    public DataSource datasource() {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://127.0.0.1:13306/sakila?useCursorFetch=true");
        config.setUsername("docker");
        config.setPassword("docker");

        config.setPoolName("READONLY-POOL");

        config.setReadOnly(true);
        config.setMaximumPoolSize(1);

        return new HikariDataSource(config) {
            @Override
            public Connection getConnection() throws SQLException {
                Connection connection = super.getConnection();
                // jobRepositoryがcommitしてもResultSetのcursorが閉じないように設定を変更する
                connection.setHoldability(HOLD_CURSORS_OVER_COMMIT);
                return connection;
            }
        };
    }

    @Bean
    public Settings jooqSettings() {
        Settings settings = new Settings();
        // ここでfetchSizeの設定をしておくと、いちいちjooqでfetchするときにfetchSizeを指定しなくても済む
        settings.setFetchSize(10);
        return settings;
    }

    @Bean
    public DefaultConfiguration configuration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(jooqSettings());
        jooqConfiguration.setDataSource(datasource());
        jooqConfiguration.setSQLDialect(SQLDialect.MYSQL);

        return jooqConfiguration;
    }

    @Bean
    public DSLContext dslContext() {
        return new DefaultDSLContext(configuration());
    }
}
