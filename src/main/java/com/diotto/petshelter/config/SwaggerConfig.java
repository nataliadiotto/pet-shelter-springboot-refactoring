package com.diotto.petshelter.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    public SwaggerConfig() {
        System.out.println("########### SwaggerConfig carregado ###########");
    }

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("PetShelter API")
                        .version("1.0")
                        .description("API for managing pets in a Pet Shelter system"));
    }

}
