package com.tekntime.oauth.server.config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
	private static final Logger logger   = LoggerFactory.getLogger(DataSourceConfig.class);	
	
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
		logger.info("DataSource initializing .... DB ={}", DATASOURCEURL);
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(DRIVER);
        dataSourceBuilder.url(DATASOURCEURL);
        dataSourceBuilder.username(DBUSER);
        dataSourceBuilder.password(DBPASSWORD);
        return dataSourceBuilder.build();
    }
}
