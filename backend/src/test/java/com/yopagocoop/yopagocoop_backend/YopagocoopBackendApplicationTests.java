package com.yopagocoop.yopagocoop_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

@SpringBootTest
@ActiveProfiles("test")
public class YopagocoopBackendApplicationTests {

	@Configuration
	static class TestDataSourceConfig {

		@Primary
		@Bean
		public DataSource dataSource() {
			return DataSourceBuilder.create()
					.driverClassName("org.h2.Driver")
					.url("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1") // URL para H2 en memoria
					.username("sa")
					.password("")
					.build();
		}
	}

	@Test
	void contextLoads() {
	}
}