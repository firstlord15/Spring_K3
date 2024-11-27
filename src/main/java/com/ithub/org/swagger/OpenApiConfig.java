package com.ithub.org.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "MicroService Api",
                description = "API микросервиса",
                version = "1.0.0",
                contact = @Contact(
                        name = "Ashimov Ratmir",
                        email = "ashimovr21@st.ithub.ru",
                        url = "https://github.com/firstlord15/Spring_K3"
                )
        )
)
public class OpenApiConfig {
    
}