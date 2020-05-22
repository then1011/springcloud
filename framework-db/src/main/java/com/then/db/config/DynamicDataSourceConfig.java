package com.then.db.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.then.db.config.datasource.DynamicDataSource;
import com.then.db.config.datasource.DynamicDataSourceTransactionManager;
import com.then.db.config.datasource.DynamicPlugin;
import com.then.db.config.datasource.HikariDataSourceContainer;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DynamicDataSourceConfig {
	
	@Autowired
	private DbConfig dbConfig;

	@Bean(name = "readDataSource")
	public HikariDataSourceContainer readDataSource() {
		HikariDataSourceContainer hikariDataSourceContainer = new HikariDataSourceContainer();
		if(StringUtils.isEmpty(dbConfig.getReadDriverClassName())|| StringUtils.isEmpty(dbConfig.getReadUrl())) {
			return hikariDataSourceContainer;
		}
		
		HikariDataSource hikariDataSource = new HikariDataSource();
		hikariDataSource.setDriverClassName(dbConfig.getReadDriverClassName());
		hikariDataSource.setJdbcUrl(dbConfig.getReadUrl());
		hikariDataSource.setUsername(dbConfig.getReadUsername());
		hikariDataSource.setPassword(dbConfig.getReadPassword());

		hikariDataSource.setMinimumIdle(dbConfig.getMinimumIdle());
		hikariDataSource.setMaximumPoolSize(dbConfig.getMaximumPoolSize());
		hikariDataSource.setAutoCommit(dbConfig.isAutoCommit());
		hikariDataSource.setIdleTimeout(dbConfig.getIdleTimeout());
		hikariDataSource.setPoolName(dbConfig.getPoolName());
		hikariDataSource.setMaxLifetime(dbConfig.getMaxLifetime());
		hikariDataSource.setConnectionTimeout(dbConfig.getConnectionTimeout());
		hikariDataSource.setConnectionTestQuery(dbConfig.getConnectionTestQuery());
		
		hikariDataSourceContainer.setCanUse(true);
		hikariDataSourceContainer.setDataSource(hikariDataSource);
		return hikariDataSourceContainer;
	}
	
	@Bean(name = "writeDataSource")
	public HikariDataSourceContainer writeDataSource() {
		HikariDataSource hikariDataSource = new HikariDataSource();
		hikariDataSource.setDriverClassName(dbConfig.getWriteDriverClassName());
		hikariDataSource.setJdbcUrl(dbConfig.getWriteUrl());
		hikariDataSource.setUsername(dbConfig.getWriteUsername());
		hikariDataSource.setPassword(dbConfig.getWritePassword());

		hikariDataSource.setMinimumIdle(dbConfig.getMinimumIdle());
		hikariDataSource.setMaximumPoolSize(dbConfig.getMaximumPoolSize());
		hikariDataSource.setAutoCommit(dbConfig.isAutoCommit());
		hikariDataSource.setIdleTimeout(dbConfig.getIdleTimeout());
		hikariDataSource.setPoolName(dbConfig.getPoolName());
		hikariDataSource.setMaxLifetime(dbConfig.getMaxLifetime());
		hikariDataSource.setConnectionTimeout(dbConfig.getConnectionTimeout());
		hikariDataSource.setConnectionTestQuery(dbConfig.getConnectionTestQuery());
		
		HikariDataSourceContainer hikariDataSourceContainer = new HikariDataSourceContainer();
		hikariDataSourceContainer.setCanUse(true);
		hikariDataSourceContainer.setDataSource(hikariDataSource);
		return hikariDataSourceContainer;
	}
	
    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource(@Qualifier("readDataSource") HikariDataSourceContainer readDataSource, @Qualifier("writeDataSource") HikariDataSourceContainer writeDataSource) {
    	DynamicDataSource dynamicDataSource = new DynamicDataSource();
    	if(readDataSource.isCanUse()) {
    		dynamicDataSource.setReadDataSource(readDataSource.getDataSource());
    	}else {
    		dynamicDataSource.setReadDataSource(writeDataSource.getDataSource());
    	}
    	dynamicDataSource.setWriteDataSource(writeDataSource.getDataSource());
        return dynamicDataSource;
    }
    
	@Bean
	@Primary
	public SqlSessionFactory generateSqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dataSource, DynamicPlugin dynamicPlugin) throws Exception {
		MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		try {
			factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(dbConfig.getMapperLocations()));
		} catch (Exception e) {
			//不需要处理
		}
		factoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(dbConfig.getConfigLocation()));
		
		return factoryBean.getObject();
	}
	
    @Bean
    public DynamicPlugin dynamicPluginInterceptor() {
        return new DynamicPlugin();
    }
	
	@Bean
    public DynamicDataSourceTransactionManager transactionManager(@Qualifier("dynamicDataSource") DataSource dataSource) { 
        return new DynamicDataSourceTransactionManager(dataSource); 
    }
}
