package com.unmsm.sistemas.integracion.isg5.config.endpoints;

import lombok.Getter;
import lombok.Setter;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndpointsConfig {

    @Bean
    @ConfigurationProperties(prefix = "http-client.ogit-resource")
    public Endpoint ogitResourceEndpoint() {
            return new Endpoint();
    }

    @Getter
    @Setter
    public static class Endpoint {
         private String baseUrl;
         private long connectTimeout;
         private long readTimeout;
         private HttpLoggingInterceptor.Level loggingLevel;
    }
}
