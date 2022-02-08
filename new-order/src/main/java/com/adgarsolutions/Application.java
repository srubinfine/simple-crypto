package com.adgarsolutions;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "Orders",
                version = "1.0",
                description = "Orders API",
                license = @License(name = "Apache 2.0", url = "https://simplecrypto.com"),
                contact = @Contact(url = "https://simplecrypto.com", name = "SC Team", email = "support@gsimplecrypto.com")
        )
)
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
