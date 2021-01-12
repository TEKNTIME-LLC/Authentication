package com.tekntime.jwt.authorization.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataSourceConfig {
	@Value("${spring.sql.driver}")
	private String DRIVER;
	@Value("${spring.data.source.url}")
	private String DATASOURCEURL;
	@Value("${spring.data.source.user}")
	private String DBUSER;
	@Value("${spring.data.source.password}")
	private String DBPASSWORD;
	
	@Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(DRIVER);
        dataSourceBuilder.url(DATASOURCEURL);
        dataSourceBuilder.username(DBUSER);
        dataSourceBuilder.password(DBPASSWORD);
        return dataSourceBuilder.build();
    }
	
}
