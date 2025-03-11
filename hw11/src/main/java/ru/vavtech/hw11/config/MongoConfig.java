package ru.vavtech.hw11.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.convert.NoOpDbRefResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import com.mongodb.reactivestreams.client.MongoClient;

@Configuration
public class MongoConfig {

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate(MongoClient mongoClient) {
        MongoMappingContext mappingContext = new MongoMappingContext();
        mappingContext.setAutoIndexCreation(true);
        
        MappingMongoConverter converter = new MappingMongoConverter(
            NoOpDbRefResolver.INSTANCE, mappingContext);
        converter.setCustomConversions(new MongoCustomConversions());
        
        return new ReactiveMongoTemplate(mongoClient, "library", converter);
    }
} 