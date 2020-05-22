package com.then.db.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class DbConfig {

	@Value("${spring.datasource.hikari.minimum-idle:10}")
	private int minimumIdle;// 最小空闲连接数量

	@Value("${spring.datasource.hikari.maximum-pool-size:50}")
	private int maximumPoolSize;// 连接池最大连接数，默认是10

	@Value("${spring.datasource.hikari.auto-commit:true}")
	private boolean autoCommit;// 此属性控制从池返回的连接的默认自动提交行为,默认值：true

	@Value("${spring.datasource.hikari.idle-timeout:30000}")
	private int idleTimeout;// 空闲连接存活最大时间，默认600000（10分钟）

	@Value("${spring.datasource.hikari.pool-name:ThenHikariCP}")
	private String poolName;// 连接池名字

	@Value("${spring.datasource.hikari.max-lifetime:1800000}")
	private int maxLifetime;// 此属性控制池中连接的最长生命周期，值0表示无限生命周期，默认1800000即30分钟

	@Value("${spring.datasource.hikari.connection-timeout:30000}")
	private int connectionTimeout;// 数据库连接超时时间,默认30秒，即30000

	@Value("${spring.datasource.hikari.connection-test-query:SELECT 1}")
	private String connectionTestQuery;

	@Value("${mybatis.config-location:classpath:mybatis.xml}")
	private String configLocation;

	@Value("${mybatis.mapper-locations:classpath:mapper/*/*.xml}")
	private String mapperLocations;
	
	/**
	 * 只读数据库
	 */
	@Value("${spring.datasource.read.driver-class-name:}")
	private String readDriverClassName;
	@Value("${spring.datasource.read.url:}")
	private String readUrl;
	@Value("${spring.datasource.read.username:}")
	private String readUsername;
	@Value("${spring.datasource.read.password:}")
	private String readPassword;
	
	/**
	 * 只写数据库
	 */
	@Value("${spring.datasource.write.driver-class-name}")
	private String writeDriverClassName;
	@Value("${spring.datasource.write.url}")
	private String writeUrl;
	@Value("${spring.datasource.write.username:}")
	private String writeUsername;
	@Value("${spring.datasource.write.password:}")
	private String writePassword;

}
