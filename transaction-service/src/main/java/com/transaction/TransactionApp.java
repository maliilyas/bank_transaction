package com.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import org.apache.commons.validator.routines.IBANValidator;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class TransactionApp extends ResourceConfig {

  public TransactionApp(@Context ServletContext servletContext) {
    super();
    //register(new ObjectMapperContextResolver());
    OpenAPI oas = new OpenAPI();

    // Info for the Swagger ui
    Info info = new Info()
        .title("Transaction Service")
        .description("Transaction service is responsible for checking the balance and handling the transactions for the customer(s).")
        .contact(new Contact()
            .email("aliilyas.pk@gmail.com"))
        .license(new License()
            .name("MIT")
            .url("https://opensource.org/licenses/MIT"));

    oas.info(info);

    SwaggerConfiguration oasConfig = new SwaggerConfiguration()
        .openAPI(oas)
        .prettyPrint(true)
        .resourcePackages(Stream.of("com.transaction.controller").collect(Collectors.toSet()));

    OpenApiResource openApiResource = new OpenApiResource();
    openApiResource.setOpenApiConfiguration(oasConfig);
    register(openApiResource);
    packages("com.transaction.controller");
  }
}
