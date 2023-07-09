package br.com.insidesoftwares.dayoffmarker.configuration.application;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

	@Bean
	public OpenAPI myOpenAPI() {
		Contact contact = new Contact();
		contact.setEmail("samuel.cunha@insidesoftwares.com");
		contact.setName("Samuel Cunha");
		contact.setUrl("https://github.com/InsideSoftwares");

		License mitLicense = new License().name("License - GNU").url("http://licencas.softwarelivre.org/gpl-3.0.pt-br.html");

		Info info = new Info()
			.title("Day Off Marker")
			.version("1.0")
			.contact(contact)
			.description("""
				The purpose of the system is to improve date management by city, state, and country, as well as to control each branch of the company for better management.
				It provides calculations, queries, and date validations to optimize processes that require dates, such as invoice generation, service orders, and scheduling routines,
				aiming to reduce conflicts where critical operations are scheduled for weekends or holidays, or generating invoices with due dates in similar situations.
				""")
			.termsOfService("http://licencas.softwarelivre.org/gpl-3.0.pt-br.html")
			.license(mitLicense);

		return new OpenAPI().info(info);
	}

}
