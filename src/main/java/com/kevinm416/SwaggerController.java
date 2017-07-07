package com.kevinm416;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
@Controller
public class SwaggerController {

    private static final String API_DOCS_URI = "/v2/api-docs";

    final private ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    private WebApplicationContext applicationContext;

    @Test
    public void testSwagger() throws Exception {
        String swaggerJson = this.mvc()
                .perform(
                        MockMvcRequestBuilders.get(
                                API_DOCS_URI)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString();
        Object swagger = objectMapper.readValue(swaggerJson, Object.class);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(System.out, swagger);
    }

    private MockMvc mvc() {
        return MockMvcBuilders.webAppContextSetup(applicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true), "/*")
                .build();
    }

    @Configuration
    @EnableSwagger2
    @EnableWebMvc
    @ComponentScan("com.kevinm416")
    public static class DocsConfig {

        public DocsConfig() {
            super();
        }

        @Bean
        public Docket getSwagger() {
            Contact contact = null;
            final ApiInfo apiInfo = new ApiInfo(
                    null,
                    null,
                    null,
                    null,
                    contact,
                    null,
                    null);
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo)
                    .genericModelSubstitutes(Optional.class);
        }

    }

}
