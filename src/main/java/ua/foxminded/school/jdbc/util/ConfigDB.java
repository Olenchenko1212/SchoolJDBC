package ua.foxminded.school.jdbc.util;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import ua.foxminded.schooljdbc.dao.StudentDaoImpl;

@Configuration
//@ComponentScan("ua.foxminded.school")
@PropertySource("classpath:application.properties")

public class ConfigDB {
	
//		@Autowired
//		private Environment environment;
//
//		private final String URL = "url";
//		private final String USER = "dbuser";
//		private final String DRIVER = "driverClassName";
//		private final String PASSWORD = "dbpassword";
//		
//		@Bean
//		public DataSource dataSource() {
//			DriverManagerDataSource dataSource = new DriverManagerDataSource();
//			dataSource.setDriverClassName(environment.getProperty(DRIVER));
//			dataSource.setUrl(environment.getProperty(URL));
//			dataSource.setUsername(environment.getProperty(USER));
//			dataSource.setPassword(environment.getProperty(PASSWORD));
//			return dataSource;
//		}
//		
	
	@Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
	
	
//	    @Bean
//	    @Primary
//	    public DataSource dataSource() {
//	      return DataSourceBuilder
//	    		  .create()
//	    		  .build();
//	    }

		@Bean
	    public JdbcTemplate jdbcTemplate() {
	        return new JdbcTemplate(dataSource());
	    }
		
		@Bean
	    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
	        return new NamedParameterJdbcTemplate(dataSource());
	    }	
}
