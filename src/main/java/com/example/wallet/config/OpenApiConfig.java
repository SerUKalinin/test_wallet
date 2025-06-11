package com.example.wallet.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация OpenAPI (Swagger) для документации API.
 * Этот класс настраивает метаданные API, включая заголовок, описание, контактную информацию и лицензию.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Создает и настраивает объект OpenAPI с метаданными API.
     *
     * @return настроенный объект OpenAPI с информацией о проекте
     */
    @Bean
    public OpenAPI walletOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Wallet API")
                        .description("API для управления электронными кошельками")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Wallet Team")
                                .email("support@wallet.com")
                                .url("https://wallet.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
} 