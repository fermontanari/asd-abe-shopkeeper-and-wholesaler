package wholesaler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import wholesaler.controller.WholesalerController;
import wholesaler.order.ProductsOrderRepository;
import wholesaler.product.ProductRepository;
import wholesaler.proposal.ProposalRepository;
@Import({springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration.class})
@EnableSwagger2
@SpringBootApplication
@ComponentScan(basePackageClasses = {
		WholesalerController.class, ProposalRepository.class, ProductsOrderRepository.class, ProductRepository.class
})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Docket libraryApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.apiInfo(metaData());
	}

	private ApiInfo metaData() {
		ApiInfo apiInfo = new ApiInfo(
				"Wholesalers REST API",
				"Spring Boot REST API for Wholesalers",
				"1.0",
				"Terms of service",
				new Contact("Fernanda Montanari e Renato Martins", "", "fmontanarisa@gmail.com"),
				"Apache License Version 2.0",
				"https://www.apache.org/licenses/LICENSE-2.0");
		return apiInfo;
	}
}