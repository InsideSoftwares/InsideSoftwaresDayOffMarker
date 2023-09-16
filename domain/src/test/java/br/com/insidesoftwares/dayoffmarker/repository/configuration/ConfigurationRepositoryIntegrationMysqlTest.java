package br.com.insidesoftwares.dayoffmarker.repository.configuration;

import br.com.insidesoftwares.dayoffmarker.RepositoryTestApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = RepositoryTestApplication.class)
@Testcontainers
@ActiveProfiles("mysql")
class ConfigurationRepositoryIntegrationMysqlTest extends ConfigurationRepositoryIntegrationTest {

	@Container
	static MySQLContainer container = new MySQLContainer("mysql:latest");

	@DynamicPropertySource
	private static void setupProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.username", container::getUsername);
		registry.add("spring.datasource.password", container::getPassword);
		registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
	}
}
