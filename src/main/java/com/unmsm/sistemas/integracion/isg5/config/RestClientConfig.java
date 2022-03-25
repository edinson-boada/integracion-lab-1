package com.unmsm.sistemas.integracion.isg5.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.unmsm.sistemas.integracion.isg5.config.endpoints.EndpointsConfig;
import com.unmsm.sistemas.integracion.isg5.proxy.api.OgitSelSecurityAPI;
import com.unmsm.sistemas.integracion.isg5.proxy.api.OgitLoginAPI;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

@Configuration
public class RestClientConfig {

    @Bean
    OgitLoginAPI ogitLoginAPI(EndpointsConfig.Endpoint ogitResourceEndpoint) {
        return getRetrofitConfig(ogitResourceEndpoint)
                .addConverterFactory(JacksonConverterFactory.create(getObjectMapper(new ObjectMapper()))).build()
                .create(OgitLoginAPI.class);
    }

    @Bean
    OgitSelSecurityAPI ogitIncidentsAPI(EndpointsConfig.Endpoint ogitResourceEndpoint) {
        return getRetrofitConfig(ogitResourceEndpoint)
                .addConverterFactory(JacksonConverterFactory.create(getObjectMapper(new ObjectMapper()))).build()
                .create(OgitSelSecurityAPI.class);
    }

    private Retrofit.Builder getRetrofitConfig(EndpointsConfig.Endpoint endpoint) {
        return new Retrofit.Builder()
                .baseUrl(endpoint.getBaseUrl())
                .client(getHttpClient(endpoint.getLoggingLevel(),
                        endpoint.getReadTimeout(),
                        endpoint.getConnectTimeout()));
    }

    private OkHttpClient getHttpClient(HttpLoggingInterceptor.Level level, long readTimeout, long connectTimeout) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(level);
        return new OkHttpClient.Builder()
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    private ObjectMapper getObjectMapper(ObjectMapper objectMapper) {
        return objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setDateFormat(new StdDateFormat())
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                .enable(JsonGenerator.Feature.IGNORE_UNKNOWN)
                .enable(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_COMMENTS);
    }
}
