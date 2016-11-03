package com.kevinm416;

import javax.inject.Inject;

import org.joda.time.DateTimeZone;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;

import springfox.documentation.schema.AlternateTypeRule;
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

    @RequestMapping(
            value = "/getStuff",
            method = RequestMethod.POST)
    @ResponseBody
    public StringResponse getStuff() {
        return new StringResponse(Optional.of(DateTimeZone.UTC), Optional.of("abc"), "def");
    }

    public static class StringResponse {
        private final Optional<DateTimeZone> dtz;
        private final Optional<String> optionalString;
        private final String nonOptionalString;

        @JsonCreator
        public StringResponse(
                @JsonProperty("dtz") Optional<DateTimeZone> dtz,
                @JsonProperty("optionalString") Optional<String> optionalString,
                @JsonProperty("nonOptionalString") String nonOptionalString) {
            this.dtz = dtz;
            this.optionalString = optionalString;
            this.nonOptionalString = nonOptionalString;
        }

        public Optional<DateTimeZone> getDtz() {
            return dtz;
        }

        public Optional<String> getOptionalString() {
            return optionalString;
        }

        public String getNonOptionalString() {
            return nonOptionalString;
        }
    }

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
            TypeResolver resolver = new TypeResolver();
            ResolvedType dtzType = resolver.resolve(DateTimeZone.class);
            ResolvedType optionalDtzType = resolver.resolve(Optional.class, DateTimeZone.class);
            ResolvedType stringType = resolver.resolve(String.class);
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo)
                    .genericModelSubstitutes(Optional.class)
                    .directModelSubstitute(DateTimeZone.class, String.class)
                    .alternateTypeRules(
                            new AlternateTypeRule(dtzType, stringType),
                            new AlternateTypeRule(optionalDtzType, stringType));
        }

    }

}
