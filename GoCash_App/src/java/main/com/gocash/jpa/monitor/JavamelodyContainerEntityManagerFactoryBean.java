package com.gocash.jpa.monitor;

import javax.sql.DataSource;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import net.bull.javamelody.JdbcWrapper;

public class JavamelodyContainerEntityManagerFactoryBean extends  LocalContainerEntityManagerFactoryBean {
	
	@Override
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(JdbcWrapper.SINGLETON.createDataSourceProxy(dataSource));
	}

}
