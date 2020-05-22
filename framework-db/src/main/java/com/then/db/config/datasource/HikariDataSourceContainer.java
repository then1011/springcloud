package com.then.db.config.datasource;

import com.zaxxer.hikari.HikariDataSource;

import lombok.Data;

@Data
public class HikariDataSourceContainer {
	
	private boolean canUse;
	private HikariDataSource dataSource;

}
