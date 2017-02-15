package com.sum.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@Configuration
@PropertySource("classpath:app-dev.properties")
public class DatabaseConfig {
	
	@Autowired
	public Environment env;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		System.out.println("> entityManagerFactory");
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setShowSql(Boolean.getBoolean(env.getProperty("hibernate.show_sql")));
		adapter.setGenerateDdl(Boolean.getBoolean(env.getProperty("hibernate.generate_ddl")));
		
		Properties props = new Properties();
		props.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
		
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(dataSource());
		emfb.setPersistenceUnitName("entityManagerFactory");
		emfb.setPackagesToScan("com.sum.domain");
		emfb.setJpaVendorAdapter(adapter);
		emfb.setJpaProperties(props);
		System.out.println("< entityManagerFactory");
		return emfb;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
	
	@Bean
	public DataSource dataSource() {
		MysqlDataSource ds = new MysqlDataSource();
		ds.setUrl(env.getProperty("db.url"));
		ds.setUser(env.getProperty("db.user"));
		ds.setPassword(env.getProperty("db.pass"));
		return ds;
	}
}
