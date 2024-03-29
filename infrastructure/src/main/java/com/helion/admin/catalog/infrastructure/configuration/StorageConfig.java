package com.helion.admin.catalog.infrastructure.configuration;

import com.google.cloud.storage.Storage;
import com.helion.admin.catalog.infrastructure.configuration.properties.google.GoogleStorageProperties;
import com.helion.admin.catalog.infrastructure.configuration.properties.storage.StorageProperties;
import com.helion.admin.catalog.infrastructure.services.StorageService;
import com.helion.admin.catalog.infrastructure.services.impl.GCStorageService;
import com.helion.admin.catalog.infrastructure.services.local.InMemoryStorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class StorageConfig {

    @Bean
    @ConfigurationProperties(value="storage.catalogo-videos")
    public StorageProperties storageProperties(){
        return new StorageProperties();
    }

    @Bean
    @Profile({"development", "test-integration","test-e2e"})
    public StorageService localStorageApi(){
        return new InMemoryStorageService();
    }

    @Bean
    @ConditionalOnMissingBean
    public StorageService gcStorageAPI(
            final GoogleStorageProperties props,
            final Storage storage
    ) {
        return new GCStorageService(props.getBucket(), storage);
    }



}
