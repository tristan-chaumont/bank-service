package org.miage.bankservice;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankServiceApplication.class, args);
    }

    /**
     * localhost:?/swagger-ui/index.html
     */
    @Bean
    public OpenAPI reservationAPI() {
        return new OpenAPI().info(new Info()
                .title("Bank API")
                .version("1.0")
                .description("Documentation sommaire de l'API Bank 1.0"));
    }
}
